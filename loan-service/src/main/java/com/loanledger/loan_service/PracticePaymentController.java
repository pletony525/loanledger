package com.loanledger.loan_service;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController 
public class PracticePaymentController {
    @PostMapping("/practice/payments")
    public PaymentRequest receive(@Valid @RequestBody PaymentRequest request) {

        return request; 

    }
    
}
