package com.example.bank.repository.user;

import com.example.bank.model.User;
import com.example.bank.model.billingDetails.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findFirstByFio(String fio);
    Optional<User> findById(int id);

}
