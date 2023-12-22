package com.glsi.xpress.Service;
import com.glsi.xpress.Entity.Book;
import com.glsi.xpress.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private BookRepository bookRepository; // Assuming a BookRepository for database operations

    // Method to store image for a specific book
    public String storeImageForBook(Book book, MultipartFile file) throws IOException {
        // Check if the book exists
        if (book != null) {
            byte[] imageData = file.getBytes(); // Get byte array from the uploaded file
            book.setBookCover(imageData); // Set image data to the book entity
            bookRepository.save(book); // Save the book entity with the image
            return "Image stored for book ID: " + book.getId();
        } else {
            throw new IllegalArgumentException("Book not found");
        }
    }

    // Method to retrieve image for a specific book
    public byte[] getImageForBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null); // Find book by ID
        if (book != null) {
            return book.getBookCover(); // Return image data from the book entity
        }
        return null;
    }
}

