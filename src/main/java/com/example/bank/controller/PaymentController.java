package com.example.bank.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {


    @GetMapping("/make-payment")
    public String payment() {
        return "payment done";
    }
}
