package bank.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** ABSTRACTION: common behaviour for all accounts (follows the class diagram). */
public abstract class Account {

   public enum Status { ACTIVE, FROZEN, CLOSED }

   private static int counter = 1000;

   // attributes from the diagram //
   private final String accountNumber;
   private double balance;
   private final String branch;
   private final Customer customer;

   //extra attributes needed for the employee requirements and statements//
   private Status status = Status.ACTIVE;
   private final List<Transaction> transactions = new ArrayList<>();

   protected Account(Customer customer, String branch) {
      this.customer = customer;
      this.branch = branch;
      this.accountNumber = "AC" + (++counter);
   }

      // Used when loading an account from the database: keeps its saved number, balance and status. //
   protected Account(Customer customer, String branch, String accountNumber, double balance, Status status) {
      this.customer = customer;
      this.branch = branch;
      this.accountNumber = accountNumber;
      this.balance = balance;
      this.status = status;
      try {
         int n = Integer.parseInt(accountNumber.substring(2));
         if (n > counter) counter = n;
      } 
      
      catch (NumberFormatException e) {
         // not a generated number, ignore //
      }
   }

   // Used when loading: puts a saved transaction back without changing the balance. //
   public void restoreTransaction(Transaction t) {
      transactions.add(t);
   }

   // Each subclass names its own type //
   public abstract String getAccountType();

   // OVERLOADING: two deposit methods with different parameters //
   public void deposit(double amount) {
      deposit(amount, "Deposit");
   }

   public void deposit(double amount, String description) {
      requireActive();
      if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than zero");
      balance += amount;
      transactions.add(new Transaction(description, amount, balance));
   }

   // Shared withdrawal logic. Only subclasses that implement WithdrawFunction call it. //
   protected void debit(double amount) {
      requireActive();
      if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than zero");
      if (amount > balance) throw new IllegalStateException("Insufficient funds");
      balance -= amount;
      transactions.add(new Transaction("Withdrawal", -amount, balance));
   }

   // Employee actions: freeze / unfreeze / close //
   public void freeze() {
      if (status != Status.ACTIVE) throw new IllegalStateException("Only an active account can be frozen");
      status = Status.FROZEN;
   }

   public void unfreeze() {
      if (status != Status.FROZEN) throw new IllegalStateException("Account is not frozen");
      status = Status.ACTIVE;
   }

   public void close() {
      if (status == Status.CLOSED) throw new IllegalStateException("Account is already closed");
      status = Status.CLOSED;
   }

   private void requireActive() {
      if (status != Status.ACTIVE) throw new IllegalStateException("Account is " + status);
   }

   // display //
   // Text Describing the Account //
   public String getAccountDetails() {
      return getAccountType() + " Account"
          + "\nAccount No: " + accountNumber
          + "\nBranch: " + branch
          + "\nOwner: " + customer.getDisplayName() + " (" + customer.getCustomerId() + ")"
          + String.format("\nBalance: BWP %.2f", balance)
          + "\nStatus: " + status;
   }
   
   // Diagram method: prints the details to the console //
   public void displayAccountDetails() {
      System.out.println(getAccountDetails());
   }

   // Statement for the last N months (NFR: up to 12 months). //
   public String getStatement(int months) {
      LocalDateTime from = LocalDateTime.now().minusMonths(months);
      StringBuilder sb = new StringBuilder();
      sb.append("STATEMENT - ").append(getAccountType()).append(" ").append(accountNumber)
         .append(" (last ").append(months).append(" month(s))\n");
      for (Transaction t : transactions) {
         if (!t.getTime().isBefore(from)) sb.append(t).append("\n");
      }
      sb.append(String.format("Closing balance: BWP %.2f", balance));
      return sb.toString();
   }

   public double getBalance() { 
      return balance;
   }
    
   public String getAccountNumber() {
      return accountNumber; 
   }
    
   public String getBranch() {
      return branch; 
   }
    
   public Customer getCustomer() { 
      return customer; 
   }
   
   public Status getStatus() { 
      return status; 
   }
   
   public List<Transaction> getTransactions() { 
      return new ArrayList<>(transactions);
   }

   @Override
   public String toString() {
      return getAccountType() + " - " + accountNumber + " (" + status + ") BWP " + String.format("%.2f", balance);
   }
}
