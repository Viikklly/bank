-- Создаем таблицу billing_details с SINGLE_TABLE стратегией
CREATE TABLE billing_details (
                                 id BIGSERIAL PRIMARY KEY,
                                 user_id INTEGER NOT NULL,
                                 billing_type VARCHAR(50),
                                 bd_type VARCHAR(50), -- Дискриминатор для Hibernate

    -- Общие поля для всех типов billing details
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Поля специфичные для CreditCard
                                 card_number VARCHAR(255),
                                 expiry_year VARCHAR(255),
                                 expiry_month VARCHAR(255),
                                 card_balance NUMERIC(38,0) DEFAULT 0,
                                 is_active_card BOOLEAN DEFAULT true,

    -- Поля специфичные для BankAccount
                                 account_number VARCHAR(255),
                                 bank_name VARCHAR(255),
                                 swift_code VARCHAR(255),
                                 wallet_balance NUMERIC(38,0) DEFAULT 0,
                                 is_active_account BOOLEAN DEFAULT false,

    -- Внешний ключ
                                 CONSTRAINT fk_billing_details_user
                                     FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);