package com.glsi.xpress.ServiceImpl;

import com.glsi.xpress.Entity.Loan;
import com.glsi.xpress.Repository.LoanRepository;
import com.glsi.xpress.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Loan renewLoan(Long loanId, Boolean renewed) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        if (loan.getRenewCount() < 3) {
            loan.setRenewCount(loan.getRenewCount() + 1);
            loan.setRenewed(true);
            loan.setFinishingTime(loan.getFinishingTime().plusDays(7)); // Assuming 7 days renewal
        } else {
            // Handle the case where the loan cannot be renewed further
        }
        return loanRepository.save(loan);
    }

    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public void deleteLoans(Long id) {

    }

    @Override
    public Boolean existsByLoanId(Long loanId) {
        return null;
    }

    // Additional methods as needed
}