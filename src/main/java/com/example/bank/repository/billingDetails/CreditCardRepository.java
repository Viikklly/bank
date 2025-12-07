package com.example.bank.repository.billingDetails;

import com.example.bank.model.billingDetails.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    Optional<CreditCard> findByCardNumber(String cardNumber);
}
