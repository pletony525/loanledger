package com.loanledger.loan_service.loan;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;



@Service 
public class LoanService {

    ConcurrentHashMap <UUID, Loan> loans = new ConcurrentHashMap<>();



    public Loan openLoan(long startingAmount){
        Loan loan = new Loan(startingAmount);
        loans.put(loan.getId(), loan);
        return loan;
    }
    
    public Loan getLoan(UUID loanId){
        Loan loan = loans.get(loanId);
        if (loan == null) {
            throw new LoanNotFoundException(loanId);
        }
        return loan;
    }

    public long pay(UUID loanId, long paymentAmount){
        Loan loan = getLoan(loanId);

        return loan.acceptPayment(paymentAmount);
    }



}
