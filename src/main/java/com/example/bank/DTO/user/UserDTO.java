package com.example.bank.DTO.user;

import com.example.bank.DTO.billingDetails.BillingDetailsCreateDto;
import com.example.bank.model.billingDetails.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String phoneNumber;    // для входа
    private String passwordHash;   // пароль
    private String pinHash;        // пин-код для операций
    private List<BillingDetailsCreateDto> bankAccounts;
}
