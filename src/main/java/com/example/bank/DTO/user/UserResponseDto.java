package com.example.bank.DTO.user;

import com.example.bank.enums.BillingType;
import com.example.bank.model.billingDetails.BankAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для ответа с данными пользователя")
public class UserResponseDto {

    @Schema(description = "ID пользователя", example = "1")
    private Integer id;

    @Schema(description = "ФИО пользователя", example = "Иванов Иван Иванович")
    private String fio;

    @Schema(description = "Номер телефона", example = "+79123456789")
    private String phoneNumber;

    @Schema(description = "Номера счетов", example = "+79123456789")
    private HashMap<Long, BillingType> userBillingTypes;

}
