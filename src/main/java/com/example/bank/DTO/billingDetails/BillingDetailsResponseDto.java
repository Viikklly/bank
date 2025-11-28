package com.example.bank.DTO.billingDetails;

import com.example.bank.enums.BillingType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditCardResponseDto.class, name = "CREDIT_CARD"),
        @JsonSubTypes.Type(value = BankAccountResponseDto.class, name = "BANK_ACCOUNT")
})
public class BillingDetailsResponseDto {
    private Long id;
    private Integer userId;
    private BillingType type;
    /// номер счета или банковский номер
    private String number;
}
