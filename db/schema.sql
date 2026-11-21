-- Banking app schema (H2). One table per class, linked by foreign keys.

CREATE TABLE IF NOT EXISTS customers (
   customer_id    VARCHAR(10)  PRIMARY KEY,
   customer_type  VARCHAR(12)  NOT NULL,
   address        VARCHAR(200),
   phone_number   VARCHAR(30),
   email          VARCHAR(100) NOT NULL UNIQUE,
   password       VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS individual_customers (
   customer_id    VARCHAR(10)  PRIMARY KEY,
   firstname      VARCHAR(60)  NOT NULL,
   surname        VARCHAR(60)  NOT NULL,
   national_id    VARCHAR(30)  NOT NULL,
   date_of_birth  DATE,
   employed       BOOLEAN      NOT NULL DEFAULT FALSE,
   FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS company_customers (
   customer_id         VARCHAR(10)  PRIMARY KEY,
   company_name        VARCHAR(100) NOT NULL,
   registration_number VARCHAR(40)  NOT NULL,
   company_type        VARCHAR(60),
   contact_person      VARCHAR(100),
   FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS accounts (
   account_number VARCHAR(10)   PRIMARY KEY,
   account_type   VARCHAR(12)   NOT NULL,
   branch         VARCHAR(100),
   balance        DECIMAL(15,2) NOT NULL DEFAULT 0,
   status         VARCHAR(10)   NOT NULL,
   customer_id    VARCHAR(10)   NOT NULL,
   FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cheque_accounts (
   account_number  VARCHAR(10)  PRIMARY KEY,
   company_name    VARCHAR(100) NOT NULL,
   company_address VARCHAR(200),
   FOREIGN KEY (account_number) REFERENCES accounts(account_number) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS savings_accounts (
   account_number VARCHAR(10)  PRIMARY KEY,
   interest_rate  DECIMAL(8,6) NOT NULL,
   FOREIGN KEY (account_number) REFERENCES accounts(account_number) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS investment_accounts (
   account_number          VARCHAR(10)   PRIMARY KEY,
   interest_rate           DECIMAL(8,6)  NOT NULL,
   minimum_opening_deposit DECIMAL(15,2) NOT NULL,
   FOREIGN KEY (account_number) REFERENCES accounts(account_number) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS transactions (
   transaction_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
   account_number   VARCHAR(10)   NOT NULL,
   transaction_time TIMESTAMP     NOT NULL,
   transaction_type VARCHAR(30)   NOT NULL,
   amount           DECIMAL(15,2) NOT NULL,
   balance_after    DECIMAL(15,2) NOT NULL,
   FOREIGN KEY (account_number) REFERENCES accounts(account_number) ON DELETE CASCADE
);
