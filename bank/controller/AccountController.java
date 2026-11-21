package bank.controller;
import bank.model.Account;
import bank.model.Bank;
import bank.model.ChequeAccount;
import bank.model.Customer;
import bank.model.InvestmentAccount;
import bank.model.SavingsAccount;
import bank.model.WithdrawFunction;
import java.util.List;

public class AccountController {
   private final Bank bank;

   public AccountController(Bank bank) {
      this.bank = bank;
   }

   public SavingsAccount openSavings(Customer customer, String branch, String depositText) {
      SavingsAccount a = new SavingsAccount(customer, branch, parseAmount(depositText));
      register(customer, a);
      return a;
   }

   public InvestmentAccount openInvestment(Customer customer, String branch, String depositText) {
      InvestmentAccount a = new InvestmentAccount(customer, branch, parseAmount(depositText));
      register(customer, a);
      return a;
   }

   public ChequeAccount openCheque(Customer customer, String branch, String depositText,
         String companyName, String companyAddress) {
      if (!customer.isEligibleForCheque())
         throw new IllegalStateException("Only employed individuals can open a cheque account");
      ChequeAccount a = new ChequeAccount(customer, branch, parseAmount(depositText), companyName, companyAddress);
      register(customer, a);
      return a;
   }

   public void deposit(Account account, String amountText) {
      account.deposit(parseAmount(amountText));
   }

   public void withdraw(Account account, String amountText) {
      if (!(account instanceof WithdrawFunction))
         throw new IllegalStateException(account.getAccountType() + " accounts do not allow withdrawals");
      ((WithdrawFunction) account).withdraw(parseAmount(amountText));
   }

   public List<Account> getAccounts(Customer customer) {
      return customer.getAccount();
   }

   public String getAccountDetails(Account account) {
      return account.getAccountDetails();
   }

   public String getStatement(Account account, int months) {
      if (months < 1 || months > 12)
         throw new IllegalArgumentException("Months must be between 1 and 12");
      return account.getStatement(months);
   }

   private void register(Customer customer, Account account) {
      customer.openAccount(account);
      bank.addAccount(account);
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