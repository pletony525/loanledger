package com.loanledger.loan_service.loan;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public record OpenLoanRequest(
    @NotNull @Positive Long amountCents
){}
