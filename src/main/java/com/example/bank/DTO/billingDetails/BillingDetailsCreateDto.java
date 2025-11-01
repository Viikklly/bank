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
@Schema
public class BillingDetailsCreateDto {
    public BillingType billingType;
    public Long id;
    public String param1;
    public String param2;
    public String param3;
    public Integer idUser;
}