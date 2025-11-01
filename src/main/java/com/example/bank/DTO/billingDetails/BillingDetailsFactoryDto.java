package com.example.bank.DTO.billingDetails;


import com.example.bank.enums.BillingType;
import com.example.bank.model.billingDetails.BankAccount;
import com.example.bank.model.billingDetails.BillingDetails;
import com.example.bank.model.billingDetails.CreditCard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/*
Помечает класс как утилитный.
Утилитный класс — это класс, который предоставляет набор статических методов
 для выполнения общих задач, не имеет состояния и не позволяет создавать экземпляры
 */
//@UtilityClass
@Service
@AllArgsConstructor
public class BillingDetailsFactoryDto {

    /// Преобразуем BillingDetais in DTO
    public BillingDetailsResponseDto billingDetailToResponseDTO(BillingDetails billingDetails) {


        if (billingDetails instanceof BankAccount bankAccount) {
            return BankAccountResponseDto.builder()
                    .id(bankAccount.getId())
                    .userId(bankAccount.getUser().getId())
                    .accountNumber(bankAccount.getAccountNumber())
                    .bankName(bankAccount.getBankName())
                    .swiftCode(bankAccount.getSwiftCode())
                    .type(BillingType.BANK_ACCOUNT)
                    .build();

        } else if (billingDetails instanceof CreditCard creditCard) {
            return CreditCardResponseDto.builder()
                    .id(creditCard.getId())
                    .userId(creditCard.getUser().getId())
                    .cardNumber(creditCard.getCardNumber())
                    .expiryYear(creditCard.getExpiryYear())
                    .expiryMonth(creditCard.getExpiryMonth())
                    .type(BillingType.CREDIT_CARD)
                    .build();
        }
        throw new IllegalArgumentException("Unknown BillingDetails type: " + billingDetails.getClass().getName());
    }
}

