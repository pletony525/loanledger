package com.loanledger.loan_service.loan;

import java.util.UUID;
public record LoanResponse(
    UUID id,
    long amountCents,
    long balanceCents,
    boolean paidOff
) {

    public static LoanResponse from(Loan loan){
        return new LoanResponse(
            loan.getId(),
            loan.getAmountCents(),
            loan.getBalanceCents(),
            loan.isPaid()
        );
    }

}
