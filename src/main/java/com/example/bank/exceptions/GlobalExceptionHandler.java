package com.example.bank.exceptions;

import com.example.bank.DTO.error.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@RestControllerAdvice   /// перехватчик исключений
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccountNotFound(AccountNotFoundException ex) {
        /// Логируем предупреждение
        log.warn("Account not found: {}", ex.getMessage());

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .details(ex.getDetails())
                .path(getCurrentRequestPath())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
    }

    @ExceptionHandler(AccountBlockedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccountBlocked(AccountBlockedException ex) {
        /// Логируем предупреждение
        log.warn("Account blocked: {}", ex.getMessage());

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .details(ex.getDetails())
                .accountId(ex.getAccountId())
                .path(getCurrentRequestPath())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponseDTO);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInsufficientFunds(InsufficientFundsException ex) {
        /// Логируем предупреждение
        log.warn("Insufficient funds: {}", ex.getMessage());

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .errorCode("INSUFFICIENT_FUNDS")
                .path(getCurrentRequestPath())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException ex) {
        /// Логируем предупреждение
        log.warn("Validation error: {}", ex.getMessage());

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .errorCode("VALIDATION_ERROR")
                .path(getCurrentRequestPath())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        /// Логируем с полным стектрейсом
        log.error("Internal server error: {}", ex.getMessage(), ex);

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Внутренняя ошибка сервера")
                .errorCode("INTERNAL_ERROR")
                .path(getCurrentRequestPath())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
    }

    private String getCurrentRequestPath() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest().getRequestURI(); ///Получаем URL
        } catch (IllegalStateException e) {
            return "N/A";
        }
    }
}