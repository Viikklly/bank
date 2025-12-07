package com.example.bank.service.transactional;

import com.example.bank.DTO.transaction.TransactionalClinicRequestDto;
import com.example.bank.DTO.transaction.TransactionalRequestDto;
import com.example.bank.DTO.transaction.TransactionalResponseDto;

public interface TransactionalServices {
    TransactionalResponseDto deposit(TransactionalRequestDto transactionalDto);
    TransactionalResponseDto depositClinic(TransactionalClinicRequestDto transactionalClinicRequestDto);
}
