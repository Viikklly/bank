package com.example.bank.service.visitor;

import com.example.bank.model.billingDetails.BankAccount;
import com.example.bank.model.billingDetails.CreditCard;
import org.springframework.stereotype.Component;

@Component
public class AccountTypeVisitor implements BillingDetailsVisitor<String> {

    @Override
    public String visit(CreditCard creditCard) {
        return "Кредитная карта";
    }

    @Override
    public String visit(BankAccount bankAccount) {
        return "Банковский счет";
    }
}
