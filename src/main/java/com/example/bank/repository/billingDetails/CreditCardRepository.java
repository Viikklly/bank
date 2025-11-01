package com.example.bank.repository.billingDetails;

import com.example.bank.model.billingDetails.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
}
