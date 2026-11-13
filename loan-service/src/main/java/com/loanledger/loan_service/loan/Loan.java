package com.loanledger.loan_service.loan;

import java.util.UUID;
public class Loan{
    private final UUID id;
    private final long amountCents;
    private long balanceCents;


    public Loan(long startingAmount){
    if (startingAmount <= 0){
        throw new IllegalArgumentException("Loan amount must be greater than zero");
    }
    
    this.id = UUID.randomUUID();
    this.amountCents = startingAmount;
    this.balanceCents = startingAmount;
    }
    public long getBalance(){
    return balanceCents;
    }

    public long acceptPayment(long paymentAmount){
        if (isPaid()) {
            throw new IllegalStateException("Loan is already paid off");
        }
        if (paymentAmount > balanceCents) {
            throw new IllegalArgumentException("Payment exceeds outstanding balance");
        }
        if (paymentAmount <= 0) {
            throw new IllegalArgumentException("Payment must be positive");
        }
        balanceCents -= paymentAmount;
        return balanceCents;
    }

    public boolean isPaid(){
        return balanceCents == 0;
    }


}


