package com.example.bank.DTO.transaction;

import com.example.bank.enums.TransactionType;
import com.example.bank.model.billingDetails.BillingDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Integer id;
    private BillingDetails fromAccount;
    private BillingDetails toAccount;
    private BigDecimal amount;
    private TransactionType type; // TRANSFER, PAYMENT, DEPOSIT
    private String description;
    private LocalDateTime date;
    private String status;
}
