package com.glsi.xpress.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.glsi.xpress.Entity.Loan;
import com.glsi.xpress.Service.LoanService;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "http://localhost:3000")
public class LoansController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    public Loan createLoans(@RequestBody Loan loan) {
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
