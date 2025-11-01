package com.example.bank.DTO.billingDetails;

import com.example.bank.enums.BillingType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class BillingDetailsResponseDto {
    private Long id;
    private Integer userId;
    private BillingType type;
    /// номер счета или банковский номер
    private String number;
}
