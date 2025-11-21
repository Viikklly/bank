package com.example.bank.service.user;

import com.example.bank.DTO.billingDetails.BillingDetailsCreateDto;
import com.example.bank.DTO.billingDetails.BillingDetailsResponseDto;
import com.example.bank.DTO.user.UserCreateDto;
import com.example.bank.DTO.user.UserResponseDto;
import com.example.bank.model.User;
import com.example.bank.model.billingDetails.BillingDetails;
import com.example.bank.repository.billingDetails.BankAccountRepository;
import com.example.bank.repository.user.UserRepository;
import com.example.bank.service.billingDetails.BillingDetailsService;
import com.example.bank.service.billingDetails.BillingFactory;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BillingDetailsService billingDetailsService;
    private final BillingFactory billingFactory;

    public UserResponseDto createUserWithBillingDetails(UserCreateDto userDTO) {
        // Создаем пользователя
        User user = User.builder()
                .fio(userDTO.getFio())
                .phoneNumber(userDTO.getPhoneNumber())
                .passwordHash(userDTO.getPassword())
                .pinHash(userDTO.getPin())
                .bankAccounts(new ArrayList<>()) // инициализируем пустой список
                .build();

        User savedUser = userRepository.save(user);
        log.info("Создан User с id: {}", savedUser.getId());

        // Создаем billing details через сервис
        List<BillingDetailsCreateDto> userBillingDetailsDTO = userDTO.getUserBillingDetails();
        if (userBillingDetailsDTO != null) {
            userBillingDetailsDTO.forEach(bankAccount -> {
                bankAccount.setIdUser(savedUser.getId());
                // Сервис сам сохраняет и возвращает DTO
                BillingDetailsResponseDto billingDetails = billingDetailsService.createBillingDetails(bankAccount);
            });
            log.info("Created {} billing details for user: {}", userBillingDetailsDTO.size(), savedUser.getId());
        }

        // Обновляем пользователя из базы (со всеми связями)
        User updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        return updatedUser.toUserResponseDto();
    }
}
