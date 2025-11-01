package com.example.bank.service.billingDetails;


import com.example.bank.DTO.billingDetails.BillingDetailsCreateDto;
import com.example.bank.DTO.billingDetails.BillingDetailsFactoryDto;
import com.example.bank.DTO.billingDetails.BillingDetailsResponseDto;
import com.example.bank.enums.BillingType;
import com.example.bank.model.billingDetails.BankAccount;
import com.example.bank.model.billingDetails.BillingDetails;
import com.example.bank.model.billingDetails.CreditCard;
import com.example.bank.repository.billingDetails.BankAccountRepository;
import com.example.bank.repository.billingDetails.BillingDetailsRepository;
import com.example.bank.repository.billingDetails.CreditCardRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class BillingDetailsServiceImpl implements BillingDetailsService {

    private BillingDetailsRepository billingDetailsRepository;
    private BankAccountRepository bankAccountRepository;
    private CreditCardRepository creditCardRepository;
    private BillingDetailsFactoryDto billingDetailsFactoryDto;
    private BillingFactory billingFactory;


    @Override
    public BillingDetailsResponseDto createBillingDetails(BillingDetailsCreateDto billingDetailsCreateDto) {
        // получаем Billing type
        // Создаем сущность через фабрику
        BillingDetails billingDetails = billingFactory.createBillingDetailsEntity(billingDetailsCreateDto);

        // Сохраняем в соответствующем репозитории в зависимости от типа
        BillingDetails savedEntity;
        BillingType billingType = billingDetailsCreateDto.getBillingType();

        switch (billingType) {
            case BANK_ACCOUNT:
                savedEntity = bankAccountRepository.save((BankAccount) billingDetails);
                break;
            case CREDIT_CARD:
                savedEntity = creditCardRepository.save((CreditCard) billingDetails);
                break;
            default:
                throw new IllegalArgumentException("Invalid billing type: " + billingType);
        }

        return billingDetailsFactoryDto.billingDetailToResponseDTO(savedEntity);
    }


    @Override
    public List<BillingDetailsResponseDto> getAllBillingDetails() {
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();

        return billingDetailsList.stream()
                .map(billingDetails -> {
                    try {
                        return billingDetailsFactoryDto.billingDetailToResponseDTO(billingDetails);
                    } catch (IllegalArgumentException e) {
                        log.warn("Не удалось преобразовать платежные реквизиты {}: {}",
                                billingDetails.getId(), e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
