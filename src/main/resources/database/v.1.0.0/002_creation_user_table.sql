-- Создаем таблицу users
CREATE TABLE IF NOT EXISTS users (
                       user_id SERIAL PRIMARY KEY,
                       fio VARCHAR(255),
                       phone_number VARCHAR(15) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       pin VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);