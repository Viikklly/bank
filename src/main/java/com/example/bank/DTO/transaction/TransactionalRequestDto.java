package com.example.bank.DTO.transaction;

import com.example.bank.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(
            description = "Тип финансовой операции",
            allowableValues = {"TRANSFER", "PAYMENT", "DEPOSIT"},
            example = "DEPOSIT"
    )
    private TransactionType type; // TRANSFER, PAYMENT, DEPOSIT

    @Schema(
            description = "Счет от куда",
            example = "1001"
    )
    private Long fromAccountID;

    @Schema(
            description = "Счет куда",
            example = "2002"
    )
    private Long toAccountID;

    @Schema(
            description = "Сумма операции. Должна быть положительным числом.",
            example = "1500.75",
            minimum = "0.01"
    )
    private BigDecimal amount;

    @Schema(
            description = "Примечание к операции"
    )
    private String description;
}
