package bank.controller;

import bank.model.Account;
import bank.model.Bank;
import bank.model.InterestCalculation;

public class InterestController {
   private final Bank bank;
   private final AccountController accounts;

   public InterestController(Bank bank, AccountController accounts) {
      this.bank = bank;
      this.accounts = accounts;
   }

   public String calculateInterest(String accountNumber) {
      Account account = accounts.findAccount(accountNumber);
      return String.format("Interest due: BWP %.2f", asInterest(account).calculateInterest());
   }

   public String applyInterest(String accountNumber) {
      Account account = accounts.findAccount(accountNumber);
      asInterest(account).applyInterest();
      return "Interest applied. " + account;
   }

   public int applyInterestToAll() {
      int count = 0;
      for (Account a : bank.getAccounts()) {
         if (a instanceof InterestCalculation) {
            ((InterestCalculation) a).applyInterest();
            count++;
         }
      }
      return count;
   }

   private InterestCalculation asInterest(Account account) {
      if (!(account instanceof InterestCalculation))
         throw new IllegalStateException(account.getAccountType() + " accounts do not earn interest");
      return (InterestCalculation) account;
   }
}