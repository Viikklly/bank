package com.example.bank.DTO.billingDetails;

import com.example.bank.enums.BillingType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class BankAccountResponseDto extends BillingDetailsResponseDto {
    private String accountNumber;
    private String bankName;
    private String swiftCode;

}