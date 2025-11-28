package com.example.bank.model;

import com.example.bank.DTO.transaction.TransactionDTO;
import com.example.bank.DTO.transaction.TransactionalResponseDto;
import com.example.bank.DTO.user.UserResponseDto;
import com.example.bank.enums.BillingType;
import com.example.bank.enums.TransactionType;
import com.example.bank.model.billingDetails.BankAccount;
import com.example.bank.model.billingDetails.BillingDetails;
import com.example.bank.model.billingDetails.CreditCard;
import com.example.bank.service.visitor.AccountNumberVisitor;
import com.example.bank.service.visitor.BillingDetailsVisitor;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.internal.Function;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;


@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Table(name = "transactions", schema = "bank_schema")
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private BillingDetails fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private BillingDetails toAccount;

    @Column(name = "amount")
    @DecimalMin(value = "0.01", message = "Сумма должна быть больше 0")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    @CreationTimestamp
    private LocalDateTime date;

    @Column(name = "status")
    private String status;


    /// Преобразование Transaction в TransactionalResponseDto
    public TransactionalResponseDto toResponseDto() {
        return TransactionalResponseDto.builder()
                .type(type)
                .fromAccountNumber(getAccountNumberSafely(fromAccount))
                .toAccountNumber(getAccountNumberSafely(toAccount))
                .amount(amount)
                .description(description)
                .date(date)
                .status(status)
                .build();
    }


    ///  TODO Переделать в Switch/case
    private String getAccountNumberSafely(BillingDetails account) {
        if (account == null) {
            return null;
        }

        try {
            if (account instanceof CreditCard) {
                return ((CreditCard) account).getCardNumber();
            } else if (account instanceof BankAccount) {
                return ((BankAccount) account).getAccountNumber();
            }
            // Fallback для неизвестных типов
            return "" + account.getId();

        } catch (Exception e) {
            log.warn("Ошибка при получении номера счета для account {}: {}", account.getId(), e.getMessage());
            return "" + account.getId();
        }
    }
}


