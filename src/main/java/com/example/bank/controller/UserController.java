package com.example.bank.controller;


import com.example.bank.DTO.user.UserCreateDto;
import com.example.bank.DTO.user.UserResponseDto;
import com.example.bank.service.user.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    UserServices userServices;

    @PostMapping
    public UserResponseDto createBillingDetails(@RequestBody UserCreateDto dto) {
        return userServices.createUserWithBillingDetails(dto);
    }
}
