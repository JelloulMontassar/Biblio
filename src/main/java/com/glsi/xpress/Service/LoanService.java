package com.glsi.xpress.Service;

import com.glsi.xpress.Entity.Loan;

import java.util.List;

public interface LoanService {

    //crud operations
    Loan createLoan(Loan loan);
    Loan renewLoan(Long loanId, Boolean renewed);
    Loan getLoanById(Long id);
    List<Loan> getAllLoans();


    void deleteLoans(Long id);

    Boolean existsByLoanId(Long loanId);
}
