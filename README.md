# CSE202 Banking App

Object-Oriented Analysis and Design with Java, Botswana Accountancy College.

Author: Peter Gabriel Muchena (Cse25-301)

## Part 4: Core model

The classes follow the class diagram. They hold attributes and basic business logic only. There is no GUI or database code.

## Folder layout

    bank/
      Main.java            console demo of the model
      model/               core classes (package bank.model)
        Account, SavingsAccount, InvestmentAccount, ChequeAccount
        Customer, IndividualCustomer, CompanyCustomer
        Transaction, Bank
        InterestCalculation, WithdrawFunction (interfaces)

## How to compile and run

From the repository root:

    javac bank/model/*.java bank/Main.java
    java bank.Main

## Notes

- Money uses `double` for simplicity. A real system would use `BigDecimal`.
- Passwords are stored as plain text for this assignment. A real system would store hashes.