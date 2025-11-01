package com.example.bank.service.user;

import com.example.bank.DTO.user.UserCreateDto;
import com.example.bank.DTO.user.UserResponseDto;

public interface UserServices {
    UserResponseDto createUserWithBillingDetails(UserCreateDto userDTO);
}
