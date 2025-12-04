package com.example.bank.DTO.billingDetails;

import com.example.bank.enums.BillingType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(description = "DTO для создания платежных данных")
public class BillingDetailsCreateDto {
    @Schema(description = "billingType", example = "СС-CREDIT_CARD, BA-BANK_ACCOUNT")
    public BillingType billingType;

    /// TODO посмотреть, нужен ли id
    @Schema(description = "id", example = "возможно, формируется самостоятельно")
    public Long id;

    @Schema(description = "param", example = "СС-cardNumber, BA-accountNumber")
    public String param1;

    @Schema(description = "param", example = "СС-expiryYear, BA-bankName")
    public String param2;

    @Schema(description = "param", example = "СС-expiryMonth, BA-swiftCode")
    public String param3;

    /// TODO посмотреть, нужен ли id
    @Schema(description = "idUser", example = "1")
    public Integer idUser;
}