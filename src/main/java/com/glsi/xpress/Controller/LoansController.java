package com.glsi.xpress.Controller;

import com.glsi.xpress.Entity.Book;
import com.glsi.xpress.Entity.User;
import com.glsi.xpress.Service.BookService;
import com.glsi.xpress.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.glsi.xpress.Entity.Loan;
import com.glsi.xpress.Service.LoanService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "http://localhost:3000")
public class LoansController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @PostMapping
    public Loan createLoans(@RequestBody Loan loan) {
        Long user_id  = loan.getUser().getId();
        User user = userService.getUserById(user_id);
        loan.setUser(user);
        Long book_id = loan.getBook().getId();
        Optional<Book> book = bookService.getBookById(book_id);
        loan.setBook(book.get());
        return loanService.createLoan(loan);
    }

    @GetMapping("/{id}")
    public Loan getLoansById(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @PutMapping("/{loansId}")
    public Loan updateLoans(@PathVariable Long loansId, @RequestBody Loan loan) {
        loan.setId(loansId);
        return loanService.createLoan(loan);
    }

    @DeleteMapping("/{id}")
    public void deleteLoans(@PathVariable Long id) {
        loanService.deleteLoans(id);
    }

    @GetMapping("/existsByLoanId/{loanId}")
    public Boolean existsByLoanId(@PathVariable Long loanId) {
        return  loanService.existsByLoanId(loanId);
    }

    @GetMapping("/renewLoan/{userId}/{renewed}")
    public Loan renewLoan(@PathVariable Long userId, @PathVariable Boolean renewed) {
        return loanService.renewLoan(userId, renewed);
    }

}
