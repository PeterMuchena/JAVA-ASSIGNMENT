package bank.controller;
import bank.model.Account;
import bank.model.Bank;
import bank.model.InterestCalculation;

public class InterestController {
   private final Bank bank;

   public InterestController(Bank bank) {
      this.bank = bank;
   }

   public double calculateInterest(Account account) {
      return asInterest(account).calculateInterest();
   }

   public void applyInterest(Account account) {
      asInterest(account).applyInterest();
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