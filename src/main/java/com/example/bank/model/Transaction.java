package com.example.bank.model;

import com.example.bank.enums.TransactionType;
import com.example.bank.model.billingDetails.BillingDetails;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // переименовано из userId


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_details_id") // исправлено имя колонки
    private BillingDetails billingDetails;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type; // TRANSFER, PAYMENT, DEPOSIT

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "status")
    private String status; // PENDING, COMPLETED, FAILED
}

/*
-- 3. Создаем таблицу transactions (основная таблица)
CREATE TABLE bank_schema.transactions (
                              transaction_id SERIAL PRIMARY KEY,
                              user_id INTEGER NOT NULL,
                              billing_details_id BIGINT,
                              amount DECIMAL(15,2) NOT NULL,
                              transaction_type VARCHAR(50) NOT NULL,
                              description TEXT,
                              date TIMESTAMP NOT NULL,
                              status VARCHAR(20) DEFAULT 'PENDING',

                              CONSTRAINT fk_transaction_user
                                  FOREIGN KEY (user_id) REFERENCES bank_schema.users(user_id) ON DELETE CASCADE,
                              CONSTRAINT fk_transaction_billing_details
                                  FOREIGN KEY (billing_details_id) REFERENCES bank_schema.billing_details(id) ON DELETE SET NULL

);
 */
