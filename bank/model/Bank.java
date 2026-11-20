package bank.model;

import java.util.ArrayList;
import java.util.List;

// Holds the bank's customers and accounts (core model, no GUI). //
public class Bank {

   private final List<Customer> customers = new ArrayList<>();
   private final List<Account> accounts = new ArrayList<>();

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

   // Used by the login: returns null if no customer has that email. //
   public Customer findCustomerByEmail(String email) {
      for (Customer c : customers) {
         if (c.getEmail().equalsIgnoreCase(email)) {
            return c;
         }
      }
      return null;
   }

   public List<Customer> getCustomers() {
      return new ArrayList<>(customers);
   }

   public List<Account> getAccounts() {
      return new ArrayList<>(accounts);
   }
}

