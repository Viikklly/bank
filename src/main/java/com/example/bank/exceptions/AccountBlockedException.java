package com.example.bank.exceptions;

public class AccountBlockedException extends RuntimeException {
    private final String errorCode;
    private final Long accountId;
    private final String details;

    public AccountBlockedException(String message, String errorCode, Long accountId) {
        super(message);
        this.errorCode = errorCode;
        this.accountId = accountId;
        this.details = "Account ID " + accountId + " is blocked";
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getDetails() {
        return details;
    }
}
