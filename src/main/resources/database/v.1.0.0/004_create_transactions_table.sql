CREATE TABLE IF NOT EXISTS bank_schema.transactions (
                                                        transaction_id SERIAL PRIMARY KEY,
                                                        from_account_id BIGINT NOT NULL,
                                                        to_account_id BIGINT NOT NULL,
                                                        amount DECIMAL(15,2) NOT NULL,
                                                        transaction_type VARCHAR(50) NOT NULL,
                                                        description TEXT,
                                                        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                        status VARCHAR(20) DEFAULT 'PENDING',

    -- Внешние ключи
                                                        CONSTRAINT fk_transaction_from_account
                                                            FOREIGN KEY (from_account_id) REFERENCES bank_schema.billing_details(id) ON DELETE CASCADE,
                                                        CONSTRAINT fk_transaction_to_account
                                                            FOREIGN KEY (to_account_id) REFERENCES bank_schema.billing_details(id) ON DELETE CASCADE


);