package bank.controller;
import bank.model.Bank;
import bank.model.Customer;

public class LoginController {
   private final Bank bank;
   private Customer currentCustomer;

   public LoginController(Bank bank) {
      this.bank = bank;
   }

   public Customer login(String email, String password) {
      if (email == null || email.trim().isEmpty() || password == null || password.isEmpty())
         throw new IllegalArgumentException("Email and password are required");
      Customer c = bank.findCustomerByEmail(email.trim());
      if (c == null || !c.authenticate(password))
         throw new IllegalArgumentException("Invalid email or password");
      currentCustomer = c;
      return c;
   }

   public void logout() {
      currentCustomer = null;
   }

   public Customer getCurrentCustomer() {
      return currentCustomer;
   }

   public boolean isLoggedIn() {
      return currentCustomer != null;
   }
}