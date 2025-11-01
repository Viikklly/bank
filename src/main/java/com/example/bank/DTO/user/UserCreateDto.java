package com.example.bank.DTO.user;

import com.example.bank.DTO.billingDetails.BillingDetailsCreateDto;
import com.example.bank.model.billingDetails.BillingDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для создания пользователя")
public class UserCreateDto {

    @Schema(description = "ФИО пользователя", example = "Иванов Иван Иванович")
    private String fio;

    @Schema(description = "Номер телефона", example = "+79123456789")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "Пароль", example = "mySecurePassword123")
    private String password;

    @NotBlank(message = "PIN is required")
    @Size(min = 4, max = 6, message = "PIN must be between 4 and 6 digits")
    @Schema(description = "ПИН-код", example = "1234")
    private String pin;

    @Schema(description = "Банковские данные")
    private List<BillingDetailsCreateDto> userBillingDetails;
}
