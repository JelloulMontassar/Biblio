package com.glsi.xpress.Controller;

import com.glsi.xpress.Entity.Book;
import com.glsi.xpress.Service.BookService;
import com.glsi.xpress.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    private final BookService bookService;
    private final ImageService imageService;

    @Autowired
    public BookController(BookService bookService, ImageService imageService) {
        this.bookService = bookService;
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        book.setPublishmentDate(LocalDateTime.now());
        Book savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBookData) {
        Optional<Book> optionalBook = bookService.getBookById(id);

        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();

            // Update only the fields that are present in the updatedBookData
            if (updatedBookData.getTitle() != null) {
                existingBook.setTitle(updatedBookData.getTitle());
            }
            if (updatedBookData.getAuther() != null) {
                existingBook.setAuther(updatedBookData.getAuther());
            }
            if (updatedBookData.getStatus() != null) {
                existingBook.setStatus(updatedBookData.getStatus());
            }
            if (updatedBookData.getQuantity() != -1) {

                existingBook.setQuantity(updatedBookData.getQuantity());
            }

            if (updatedBookData.getCategory() != null) {
                existingBook.setCategory(updatedBookData.getCategory());
            }
            Book updatedBook = bookService.saveBook(existingBook); // Save the updated book

            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{bookId}/image/upload")
    public ResponseEntity<String> uploadImageForBook(
            @PathVariable Long bookId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            Optional<Book> optionalBook = bookService.getBookById(bookId); // Get the book by ID
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get(); // Extract the Book object from Optional
                // Store the image for the book
                String imageUrl = imageService.storeImageForBook(book, file); // Store the image
                return ResponseEntity.ok("Image uploaded for book ID: " + bookId + " Image URL: " + imageUrl);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }


    @GetMapping("/{bookId}/image")
    public ResponseEntity<byte[]> getImageForBook(@PathVariable Long bookId) {
        try {
            Optional<Book> book = bookService.getBookById(bookId); // Get the book by ID
            if (book.isPresent() && book.get().getBookCover() != null) {
                // Retrieve the image for the book
                byte[] imageData = book.get().getBookCover(); // Get the image data from the book entity

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Set content type as image/jpeg or appropriate image type

                return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

