package com.example.bank.service.visitor;

import com.example.bank.model.billingDetails.BankAccount;
import com.example.bank.model.billingDetails.BillingDetails;
import com.example.bank.model.billingDetails.CreditCard;

public interface BillingDetailsVisitor<T> {

    /// Посещает CreditCard и извлекает нужные данные
    T visit(CreditCard creditCard);


    ///Посещает BankAccount и извлекает нужные данные
    T visit(BankAccount bankAccount);


    /// Метод для обработки любого BillingDetails
    default T visit(BillingDetails account) {
        if (account instanceof CreditCard) {
            return visit((CreditCard) account);
        } else if (account instanceof BankAccount) {
            return visit((BankAccount) account);
        }
        throw new IllegalArgumentException("Неизвестный тип счета: " + account.getClass().getSimpleName());
    }
}
