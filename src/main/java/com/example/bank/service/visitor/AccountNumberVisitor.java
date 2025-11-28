package com.example.bank.service.visitor;

import com.example.bank.model.billingDetails.BankAccount;
import com.example.bank.model.billingDetails.CreditCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountNumberVisitor implements BillingDetailsVisitor<String> {

    private static final String DEFAULT_PREFIX = "ACC_";

    @Override
    public String visit(CreditCard creditCard) {
        try {
            String cardNumber = creditCard.getCardNumber();
            if (cardNumber == null || cardNumber.trim().isEmpty()) {
                log.warn("Номер карты пуст для CreditCard ID: {}", creditCard.getId());
                return DEFAULT_PREFIX + creditCard.getId();
            }
            return cardNumber;
        } catch (Exception e) {
            log.error("Ошибка при получении номера карты для ID: {}", creditCard.getId(), e);
            return DEFAULT_PREFIX + creditCard.getId();
        }
    }

    @Override
    public String visit(BankAccount bankAccount) {
        try {
            String accountNumber = bankAccount.getAccountNumber();
            if (accountNumber == null || accountNumber.trim().isEmpty()) {
                log.warn("Номер счета пуст для BankAccount ID: {}", bankAccount.getId());
                return DEFAULT_PREFIX + bankAccount.getId();
            }
            return accountNumber;
        } catch (Exception e) {
            log.error("Ошибка при получении номера счета для ID: {}", bankAccount.getId(), e);
            return DEFAULT_PREFIX + bankAccount.getId();
        }
    }
}
