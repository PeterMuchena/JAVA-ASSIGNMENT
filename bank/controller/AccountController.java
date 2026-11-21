package bank.controller;

import bank.model.Account;
import bank.model.Bank;
import bank.model.ChequeAccount;
import bank.model.Customer;
import bank.model.InvestmentAccount;
import bank.model.SavingsAccount;
import bank.model.WithdrawFunction;
import java.util.ArrayList;
import java.util.List;

public class AccountController {
   private final Bank bank;
   private final LoginController login;

   public AccountController(Bank bank, LoginController login) {
      this.bank = bank;
      this.login = login;
   }

   public String openAccount(String type, String branch, String depositText,
         String companyName, String companyAddress) {
      Customer customer = currentCustomer();
      if (type == null)
         throw new IllegalArgumentException("Select an account type");
      if (branch == null || branch.trim().isEmpty())
         throw new IllegalArgumentException("Branch is required");
      double deposit = parseAmount(depositText);
      Account account;
      if (type.equals("Savings")) {
         account = new SavingsAccount(customer, branch.trim(), deposit);
      } else if (type.equals("Investment")) {
         account = new InvestmentAccount(customer, branch.trim(), deposit);
      } else if (type.equals("Cheque")) {
         if (!customer.isEligibleForCheque())
            throw new IllegalStateException("Only employed individuals can open a cheque account");
         account = new ChequeAccount(customer, branch.trim(), deposit, companyName, companyAddress);
      } else {
         throw new IllegalArgumentException("Unknown account type");
      }
      customer.openAccount(account);
      bank.addAccount(account);
      return "Opened: " + account;
   }

   public List<String> getAccountNumbers() {
      List<String> numbers = new ArrayList<>();
      Customer customer = login.getCurrentCustomer();
      if (customer != null) {
         for (Account a : customer.getAccount())
            numbers.add(a.getAccountNumber());
      }
      return numbers;
   }

   public String deposit(String accountNumber, String amountText) {
      Account account = findAccount(accountNumber);
      account.deposit(parseAmount(amountText));
      return "Done. " + account;
   }

   public String withdraw(String accountNumber, String amountText) {
      Account account = findAccount(accountNumber);
      if (!(account instanceof WithdrawFunction))
         throw new IllegalStateException(account.getAccountType() + " accounts do not allow withdrawals");
      ((WithdrawFunction) account).withdraw(parseAmount(amountText));
      return "Done. " + account;
   }

   public String getAccountDetails(String accountNumber) {
      return findAccount(accountNumber).getAccountDetails();
   }

   public String getStatement(String accountNumber, int months) {
      if (months < 1 || months > 12)
         throw new IllegalArgumentException("Months must be between 1 and 12");
      return findAccount(accountNumber).getStatement(months);
   }

   // used by InterestController as well //
   public Account findAccount(String accountNumber) {
      if (accountNumber == null)
         throw new IllegalArgumentException("Select an account");
      for (Account a : currentCustomer().getAccount()) {
         if (a.getAccountNumber().equals(accountNumber))
            return a;
      }
      throw new IllegalArgumentException("Account not found");
   }

   private Customer currentCustomer() {
      Customer customer = login.getCurrentCustomer();
      if (customer == null)
         throw new IllegalStateException("Please log in first");
      return customer;
   }

   private double parseAmount(String text) {
      if (text == null || text.trim().isEmpty())
         return 0;
      try {
         return Double.parseDouble(text.trim());
      } catch (NumberFormatException e) {
         throw new IllegalArgumentException("Enter a valid amount");
      }
   }
}