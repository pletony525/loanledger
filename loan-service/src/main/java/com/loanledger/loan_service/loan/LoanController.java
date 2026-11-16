package com.loanledger.loan_service.loan;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


//Function of a Controller
//Receives requests and passes to the right person and gets back answer
//4 Jobs: Map URLS to methods, Pull ID out of url and turns JSON body into record, 


@RestController 
@RequestMapping("/loans")
public class LoanController {

    // Inject the LoanService
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }



    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse openLoan(@Valid  @RequestBody OpenLoanRequest request){
        long startingAmount = request.amountCents();
        Loan loan = loanService.openLoan(startingAmount);
        return LoanResponse.from(loan);
    }

    @GetMapping("/{UUID}")
    public LoanResponse getLoan(@PathVariable("UUID") UUID uuid) {
        Loan loan = loanService.getLoan(uuid);
        return LoanResponse.from(loan);
    }

    @PostMapping("/{UUID}/pay")
    public LoanResponse payLoan(@PathVariable("UUID") UUID uuid, @Valid @RequestBody PaymentRequest request){
        loanService.pay(uuid, request.amountCents());
        return LoanResponse.from(loanService.getLoan(uuid));
    }


}















