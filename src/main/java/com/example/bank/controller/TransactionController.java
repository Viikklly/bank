package com.example.bank.controller;

import com.example.bank.DTO.transaction.TransactionalClinicRequestDto;
import com.example.bank.DTO.transaction.TransactionalRequestDto;
import com.example.bank.DTO.transaction.TransactionalResponseDto;
import com.example.bank.service.transactional.TransactionalServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    TransactionalServices transactionalServices;

    @PostMapping("/deposit")
    TransactionalResponseDto deposit(@RequestBody TransactionalRequestDto transactionalDto){
        return transactionalServices.deposit(transactionalDto);
    }

    @PostMapping("/deposit-clinic")
    TransactionalResponseDto depositClinic(@RequestBody TransactionalClinicRequestDto transactionalDto){
        return transactionalServices.depositClinic(transactionalDto);
    }

}
