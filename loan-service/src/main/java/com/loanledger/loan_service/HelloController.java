package com.loanledger.loan_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {
    @GetMapping("/hello")
    public HelloResponse hello() {
        return new HelloResponse("Hello, World!");
    }

    @GetMapping("/hello/{name}")
    public HelloResponse helloName(@PathVariable String name) {
        return new HelloResponse("Hello, " + name + "!");
    }

}
