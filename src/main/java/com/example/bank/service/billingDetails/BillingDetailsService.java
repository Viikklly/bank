package com.example.bank.service.billingDetails;

import com.example.bank.DTO.billingDetails.BillingDetailsCreateDto;
import com.example.bank.DTO.billingDetails.BillingDetailsResponseDto;

import java.util.List;

public interface BillingDetailsService {

    BillingDetailsResponseDto createBillingDetails(BillingDetailsCreateDto billingDetailsCreateDto);

    List<BillingDetailsResponseDto> getAllBillingDetails();

}
