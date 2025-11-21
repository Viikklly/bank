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
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "status")
    private String status;
}


