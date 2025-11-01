package com.example.bank.service.billingDetails;

import com.example.bank.DTO.billingDetails.BillingDetailsCreateDto;
import com.example.bank.enums.BillingType;
import com.example.bank.model.User;
import com.example.bank.model.billingDetails.BankAccount;
import com.example.bank.model.billingDetails.BillingDetails;
import com.example.bank.model.billingDetails.CreditCard;
import com.example.bank.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

// Помечает класс как утилитный.
// Это класс, который предоставляет статические
// методы для выполнения общих задач, не имеет состояния и не может быть создан
//@UtilityClass
@Service
@AllArgsConstructor
@NoArgsConstructor
public class BillingFactoryImpl implements BillingFactory {

    @Autowired
    UserRepository userRepository;


    /// Создаем BillingDetails
    public BillingDetails createBillingDetailsEntity(BillingDetailsCreateDto billingDetailsCreateDto) {
        BillingType billingType = billingDetailsCreateDto.getBillingType();

        User user = userRepository.findById(billingDetailsCreateDto.getIdUser())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User не найден по id: " + billingDetailsCreateDto.getIdUser()
                ));

        return switch (billingType) {
            case CREDIT_CARD ->
                    CreditCard.builder()
                            .user(user)
                            .billingType(billingType)
                            .cardNumber(billingDetailsCreateDto.getParam1())
                            .expiryYear(billingDetailsCreateDto.getParam2())
                            .expiryMonth(billingDetailsCreateDto.getParam3())
                            .cardBalance(BigDecimal.ZERO)
                            .isActiveCard(true)
                            .build();

            case BANK_ACCOUNT ->
                    BankAccount.builder()
                            .user(user)
                            .billingType(billingType)
                            .accountNumber(billingDetailsCreateDto.getParam1())
                            .bankName(billingDetailsCreateDto.getParam2())
                            .swiftCode(billingDetailsCreateDto.getParam3())
                            .walletBalance(BigDecimal.ZERO)
                            .isActiveAccount(true)
                            .build();

            default -> throw new IllegalArgumentException("Нет такого типа банковской операции: " + billingType);
        };
    }
}
