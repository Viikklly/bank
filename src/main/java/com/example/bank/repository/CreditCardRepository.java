package com.example.bank.repository;

import com.example.bank.model.billingDetails.CreditCard;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
}
