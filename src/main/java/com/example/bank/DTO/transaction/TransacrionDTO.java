package com.example.bank.DTO.transaction;

import com.example.bank.enums.TransactionType;
import com.example.bank.model.User;
import com.example.bank.model.billingDetails.BillingDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacrionDTO {
    private Integer id;
    private User user; // переименовано из userId
    private BillingDetails billingDetails;
    private BigDecimal amount;
    private TransactionType type; // TRANSFER, PAYMENT, DEPOSIT
    private String description;
    private LocalDateTime date;
    private String status;
}
