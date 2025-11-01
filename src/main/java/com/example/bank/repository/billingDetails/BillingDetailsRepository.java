package com.example.bank.repository.billingDetails;

import com.example.bank.model.billingDetails.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Integer> {
    List<BillingDetails> findAll();
    Optional<BillingDetailsRepository> findById(Long id);
}
