package com.example.bank.repository.billingDetails;

import com.example.bank.enums.BillingType;
import com.example.bank.model.billingDetails.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Integer> {
    ///  любой тип счета по ID
    Optional<BillingDetails> findById(Long id);

    ///  все счета пользователя (и CreditCard и BankAccount)
    List<BillingDetails> findByUserId(Long userId);

    /// счет по типу биллинга
    List<BillingDetails> findByBillingType(BillingType billingType);


}
