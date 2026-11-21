# CSE202 Banking App

Object-Oriented Analysis and Design with Java, Botswana Accountancy College.

Author: Peter Gabriel Muchena (Cse25-301)

## What it is

A banking app with a core model (Part 4) and a JavaFX GUI (Part 5). The model has no GUI or database code. The views only display data and collect input, and the controllers sit between the views and the model.

## Folder layout

    bank/
      BankApp.java         JavaFX launcher
      model/               core classes (package bank.model)
        Account, SavingsAccount, InvestmentAccount, ChequeAccount
        Customer, IndividualCustomer, CompanyCustomer
        Transaction, Bank
        InterestCalculation, WithdrawFunction (interfaces)
      controller/          LoginController, CustomerController,
                           AccountController, InterestController
      view/                LoginView, OpenAccountView, DepositView,
                           AccountView, InterestView

## How to run (GitHub Codespaces)

1. Download JavaFX (once):

        ./setup-javafx.sh

2. Compile:

        javac --module-path lib/javafx --add-modules javafx.controls -d out $(find bank -name "*.java")

3. Open the desktop: in the **Ports** tab, open port **6080** in the browser (add /vnc.html to the address if it is blank), click Connect, password `vscode`.

4. Run:

        export DISPLAY=:1
        java --module-path lib/javafx --add-modules javafx.controls --enable-native-access=javafx.graphics -cp out bank.BankApp

## Demo login

- Email: peter.muchena@example.com
- Password: 12345

## Notes

- Requires JDK 25 and JavaFX 25 (downloaded by setup-javafx.sh).
- Money uses `double` for simplicity. A real system would use `BigDecimal`.
- Passwords are stored as plain text for this assignment. A real system would store hashes.