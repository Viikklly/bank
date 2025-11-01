package com.example.bank.model;

import com.example.bank.DTO.user.UserResponseDto;
import com.example.bank.enums.BillingType;
import com.example.bank.model.billingDetails.BankAccount;
import com.example.bank.model.billingDetails.BillingDetails;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "fio")
    private String fio;

    @Column(name = "phone_number")
    private String phoneNumber;    // для входа

    @Column(name = "password")
    private String passwordHash;   // пароль

    @Column(name = "pin")
    private String pinHash;        // пин-код для операций

    @OneToMany(mappedBy = "id",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<BillingDetails> bankAccounts;


    public UserResponseDto toUserResponseDto() {

        HashMap<Long, BillingType> userBillingTypes = new HashMap<>();

        bankAccounts.forEach(billingDetails -> {
            userBillingTypes.put(billingDetails.getId(), billingDetails.getBillingType());
        });

        return UserResponseDto.builder()
                .id(id)
                .fio(fio)
                .phoneNumber(phoneNumber)
                .userBillingTypes(userBillingTypes)
                .build();
    }
}

