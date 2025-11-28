package com.example.bank.DTO.transaction;

import com.example.bank.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionalRequestDto {
    private TransactionType type; // TRANSFER, PAYMENT, DEPOSIT
    private Long fromAccountID;
    private Long toAccountID;
    private BigDecimal amount;
    private String description;
}
