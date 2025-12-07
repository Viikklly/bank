package com.example.bank.service.transactional;

import com.example.bank.DTO.transaction.TransactionalClinicRequestDto;
import com.example.bank.DTO.transaction.TransactionalRequestDto;
import com.example.bank.DTO.transaction.TransactionalResponseDto;
import com.example.bank.enums.TransactionType;
import com.example.bank.exceptions.AccountBlockedException;
import com.example.bank.exceptions.AccountNotFoundException;
import com.example.bank.exceptions.InsufficientFundsException;
import com.example.bank.exceptions.NotFoundException;
import com.example.bank.model.Transaction;
import com.example.bank.model.billingDetails.BankAccount;
import com.example.bank.model.billingDetails.BillingDetails;
import com.example.bank.model.billingDetails.CreditCard;
import com.example.bank.repository.billingDetails.BankAccountRepository;
import com.example.bank.repository.billingDetails.BillingDetailsRepository;
import com.example.bank.repository.billingDetails.CreditCardRepository;
import com.example.bank.repository.transactional.TransactionalRepository;
import com.example.bank.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class TransactionalServicesImpl implements TransactionalServices {

    @Autowired
    private BillingDetailsRepository billingDetailsRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionalRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;


    /// ПОПОЛНЕНИЕ СЧЕТА
    public TransactionalResponseDto deposit(TransactionalRequestDto transactionalDto) {
        /// Валидация DTO
        if (transactionalDto == null) {
            throw new IllegalArgumentException("Запрос не может быть null");
        }
        ///  Счет от куда
        BillingDetails fromAccount = billingDetailsRepository.findById(transactionalDto.getFromAccountID())
                .orElseThrow(() -> new AccountNotFoundException(
                        "Счет отправителя не найден",
                        "ACCOUNT_NOT_FOUND",
                        transactionalDto.getFromAccountID()
                ));
        /// Счет куда
        BillingDetails toAccount = billingDetailsRepository.findById(transactionalDto.getToAccountID())
                .orElseThrow(() -> new AccountNotFoundException(
                        "Счет получателя не найден",
                        "ACCOUNT_NOT_FOUND",
                        transactionalDto.getToAccountID()
                ));
        /// сумма перевода
        BigDecimal dtoAmount = transactionalDto.getAmount();

        /// валидация суммы транзакции
        validateTransactionAmount(dtoAmount);

        /// Проверка счетов, на существование и активность
        validateAccountForTransaction(fromAccount);
        validateAccountForTransaction(toAccount);

        /// Проверка хватает ли денег для перевода
        validateAmount(fromAccount, dtoAmount);

        ///  Перевод денег с одного баланса на другой, с учетом транзакционного подхода
        transferBetweenAccounts(fromAccount, toAccount, dtoAmount);


        /// Создаем транзакцию пополнения
        Transaction transaction = createTransaction(
                fromAccount, toAccount, dtoAmount,
                TransactionType.DEPOSIT,
                transactionalDto.getDescription() != null ? transactionalDto.getDescription() : "Пополнение счета"
        );


        Transaction savedTransaction = saveTransaction(transaction);


        log.info("Пополнение счета {} на {} успешно выполнено. Новый баланс получателя: {}",
                toAccount.getId(), dtoAmount, getAccountBalance(toAccount));

        return savedTransaction.toResponseDto();
    }




    /// ПОПОЛНЕНИЕ СЧЕТА КЛИНИКИ
    public TransactionalResponseDto depositClinic(TransactionalClinicRequestDto transactionalClinicRequestDto) {
        /// Валидация DTO
        if (transactionalClinicRequestDto == null) {
            throw new IllegalArgumentException("Запрос не может быть null");
        }

        /// получаем аккаунты
        String fromAccountNameUser = transactionalClinicRequestDto.getFromAccountPaymentNumberUser();
        String toAccountNameUser = transactionalClinicRequestDto.getToAccountPaymentNumberUser();

        BillingDetails billingDetailsFromAcc = convertToBillingDetails(fromAccountNameUser);
        BillingDetails billingDetailsToAcc = convertToBillingDetails(toAccountNameUser);


        /// валидация суммы транзакции
        BigDecimal amount = transactionalClinicRequestDto.getAmount();
        validateTransactionAmount(amount);

        /// Проверка счетов, на существование и активность
        validateAccountForTransaction(billingDetailsFromAcc);
        validateAccountForTransaction(billingDetailsToAcc);

        /// Создаем транзакцию пополнения
        Transaction transaction = createTransaction(
                billingDetailsFromAcc, billingDetailsToAcc, amount,
                TransactionType.DEPOSIT,
                transactionalClinicRequestDto.getDescription() != null ? transactionalClinicRequestDto.getDescription() : "Пополнение счета от ООО Лапки царапки"
        );

        Transaction savedTransaction = saveTransaction(transaction);


        log.info("Пополнение счета {} на {} успешно выполнено. Новый баланс получателя: {}",
                billingDetailsFromAcc.getId(), billingDetailsToAcc, getAccountBalance(billingDetailsToAcc));

        return savedTransaction.toResponseDto();
    }




    /// --- ///

    /// Валидация аккаунта перед трансакцией
    /// 1 Существование счета
    /// 2 Проверка активности
    /// 3 Проверка баланса
    public void validateAccountForTransaction(BillingDetails account) {
        /// 1 Проверка существования счета
        if (account == null) {
            throw new AccountNotFoundException("Счет не найден", "ACCOUNT_NOT_FOUND");
        }

        /// 2 Проверка активности
        if (!isAccountActive(account)) {
            String accountType = getAccountTypeDescription(account);
            throw new AccountBlockedException(
                    String.format("%s заблокирован", accountType),
                    "ACCOUNT_BLOCKED",
                    account.getId()
            );
        }

    }

    /// Активность аккаунта
    private boolean isAccountActive(BillingDetails account) {
        if (account instanceof CreditCard) {
            return ((CreditCard) account).isActiveCard();
        } else if (account instanceof BankAccount) {
            return ((BankAccount) account).isActiveAccount();
        }
        return false;
    }

    /// Сообщение о блокировке аккаунта
    private String getAccountBlockedMessage(BillingDetails account) {
        String accountType = account instanceof CreditCard ? "Кредитная карта" : "Банковский счет";
        return String.format("%s отправителя заблокирована", accountType);
    }

    /// Метод получения баланса
    /// Без ИЗМЕНЕНИЯ модели
    private BigDecimal getAccountBalance(BillingDetails account) {
        try {
            if (account instanceof CreditCard) {
                return ((CreditCard) account).getCardBalance();
            } else if (account instanceof BankAccount) {
                return ((BankAccount) account).getWalletBalance();
            }
            throw new IllegalArgumentException("Неизвестный тип счета: " + account.getClass().getSimpleName());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении баланса", e);
        }
    }

    /// Метод установки баланса
    /// Без ИЗМЕНЕНИЯ модели
    private void setAccountBalance(BillingDetails account, BigDecimal balance) {
        try {
            if (account instanceof CreditCard) {
                ((CreditCard) account).setCardBalance(balance);
            } else if (account instanceof BankAccount) {
                ((BankAccount) account).setWalletBalance(balance);
            } else {
                throw new IllegalArgumentException("Неизвестный тип счета: " + account.getClass().getSimpleName());
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при установке баланса", e);
        }
    }

    ///  Проверка баланса
    private void validateAmount(BillingDetails account, BigDecimal amount) {
        /// Баланс < Сумма транзакции (compareTo вернет -1, условие true)
        if (getAccountBalance(account).compareTo(amount) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на счете");
        }
    }

    /// Создание транзакции
    private Transaction createTransaction(BillingDetails fromAccount, BillingDetails toAccount,
                                          BigDecimal amount, TransactionType type, String description) {
        return Transaction.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .type(type)
                .description(description)
                .status("COMPLETED")
                .build();
    }


    ///  Перевод денег с одного баланса на другой, с учетом транзакционного подхода
    @Transactional
    protected void transferBetweenAccounts(BillingDetails fromAccount, BillingDetails toAccount, BigDecimal amount) {
        /// Проверка на null,
        /// fromAccount != toAccount
        /// Сумма перевода > 0
        validateTransferParameters(fromAccount, toAccount, amount);

        /// Списание с одного счета
        withdrawFromAccount(fromAccount, amount);

        /// Пополнение другого счета
        depositToAccount(toAccount, amount);
    }


    /// Проверка на null,
    /// fromAccount != toAccount
    /// Сумма перевода > 0
    private void validateTransferParameters(BillingDetails fromAccount, BillingDetails toAccount, BigDecimal amount) {
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Счета не могут быть null");
        }

        if (fromAccount.equals(toAccount)) {
            throw new IllegalArgumentException("Нельзя переводить средства на тот же самый счет");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        }
    }

    /// Списание
    private void withdrawFromAccount(BillingDetails account, BigDecimal amount) {
        BigDecimal newBalance = getAccountBalance(account).subtract(amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException(
                    String.format("Недостаточно средств для списания. Баланс: %s, сумма: %s",
                            getAccountBalance(account), amount)
            );
        }

        setAccountBalance(account, newBalance);
    }

    /// Валидация суммы
    private void validateTransactionAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Сумма не может быть null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }
    }


    /// Зачисление
    private void depositToAccount(BillingDetails account, BigDecimal amount) {
        BigDecimal newBalance = getAccountBalance(account).add(amount);

        setAccountBalance(account, newBalance);
    }

    /// Сохранение транзакции в базу данных
    private Transaction saveTransaction(Transaction transaction) {
        try {
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            log.error("Ошибка при сохранении транзакции: {}", e.getMessage());
            throw new RuntimeException("Не удалось сохранить транзакцию", e);
        }
    }

    private String getAccountTypeDescription(BillingDetails account) {
        if (account == null) return "Счет";

        if (account instanceof CreditCard) {
            return "Кредитная карта";
        } else if (account instanceof BankAccount) {
            return "Банковский счет";
        }
        return "Счет";
    }

    /// преобразовать платежный номер в Bill Det
    private BillingDetails convertToBillingDetails(String payNumber) {
        String[] payNumSplit = payNumber.split("_");

        if (payNumSplit.length != 2) {
            throw new IllegalArgumentException("Неверный формат: " + payNumber);
        }

        String type = payNumSplit[0];
        String number = payNumSplit[1];

        switch (type) {
            case "BA":
                BankAccount bankAccount = bankAccountRepository.findByAccountNumber(number)
                        .orElseThrow(() -> new NotFoundException("Банковский счет не найден"));
                return bankAccount;

            case "CC":
                CreditCard creditCard = creditCardRepository.findByCardNumber(number)
                        .orElseThrow(() -> new NotFoundException("Кредитная карта не найдена"));
                return creditCard;

            default:
                throw new IllegalArgumentException("Неизвестный тип: " + type);
        }

    }

}
