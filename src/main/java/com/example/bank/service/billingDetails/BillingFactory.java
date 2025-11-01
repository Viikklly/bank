package com.example.bank.service.billingDetails;

import com.example.bank.DTO.billingDetails.BillingDetailsCreateDto;
import com.example.bank.model.billingDetails.BillingDetails;

public interface BillingFactory {
    BillingDetails createBillingDetailsEntity(BillingDetailsCreateDto billingDetailsCreateDto);
}
