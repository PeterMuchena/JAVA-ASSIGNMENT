package bank.model;

import java.util.ArrayList;
import java.util.List;

// Holds the bank's customers and accounts (core model, no GUI). //
public class Bank {

   private int customerCounter = 1000;
   private final List<Customer> customers = new ArrayList<>();
   private final List<Account> accounts = new ArrayList<>();

   public String nextCustomerId() {
      return "CU" + (++customerCounter);
   }

   public void addCustomer(Customer customer) {
      if (customer == null) {
         throw new IllegalArgumentException("Customer is required");
      }
      customers.add(customer);
   }

   public void addAccount(Account account) {
      if (account == null) {
         throw new IllegalArgumentException("Account is required");
      }
      accounts.add(account);
   }

   public List<Customer> getCustomers() {
      return new ArrayList<>(customers);
   }

   public List<Account> getAccounts() {
      return new ArrayList<>(accounts);
   }
}