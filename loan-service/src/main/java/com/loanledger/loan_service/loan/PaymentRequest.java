package com.loanledger.loan_service.loan;
//request record

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public record PaymentRequest (
    @NotNull @Positive Long amountCents
){}

