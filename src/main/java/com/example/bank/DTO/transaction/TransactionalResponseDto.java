package com.example.bank.DTO.transaction;

import com.example.bank.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionalResponseDto {
    private TransactionType type; // TRANSFER, PAYMENT, DEPOSIT
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private String description;
    private LocalDateTime date;
    private String status;
}
