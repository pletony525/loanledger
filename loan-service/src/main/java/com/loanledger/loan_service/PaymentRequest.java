package com.loanledger.loan_service;
//request record

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
public record PaymentRequest (
    @NotNull @Positive Long amountCents,
    @Size(max = 100) String note
){}

