package com.example.bank.exceptions;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountNotFoundException extends RuntimeException {
    private final String errorCode;
    private final String details;

    public AccountNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.details = message;
    }

    public AccountNotFoundException(String message, String errorCode, Long accountId) {
        super(message);
        this.errorCode = errorCode;
        this.details = "Account ID: " + accountId;
    }

/*    public String getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }*/
}