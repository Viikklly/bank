package com.example.bank.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
id 5
{
  "fio": "ООО Клиника ЛАПКИ ЦАРАПКИ",
  "phoneNumber": "+79161234567",
  "password": "securePass789",
  "pin": "9876",
  "userBillingDetails": [
    {
      "billingType": "BANK_ACCOUNT",
      "param1": "40817810100001234567",
      "param2": "АО ТИНЬКОФФ БАНК",
      "param3": "044525974",
      "idUser": null
    }
  ]
}
* */

@AllArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {


    @GetMapping("/make-payment")
    public String payment() {
        return "payment done";
    }
}
