package bank.model;
import java.util.ArrayList;
import java.util.List;

// ABSTRACTION, a customer is either an individual or a company //
public abstract class Customer {
   private static int counter = 0;

   private final String customerId;
   private final String address;
   private final String phoneNumber;
   private final String email;      // also the login name
   private final String password;
   private final List<Account> accounts = new ArrayList<>();

   protected Customer(String address, String phoneNumber, String email, String password) {
      this.customerId = String.format("C%03d", ++counter);
      this.address = address;
      this.phoneNumber = phoneNumber;
      this.email = email;
      this.password = password;
   }

   public abstract String getCustomerType();
   
   public abstract String getDisplayName();// firstname + surname, or company name
   
   public abstract boolean isEligibleForCheque();    // only employed individuals

   public boolean authenticate(String password) {
      return this.password.equals(password);
   }

   public void openAccount(Account account) {
      accounts.add(account);
   }

   public List<Account> getAccount() {
      return accounts;
   }

   protected String baseDetails() {
      return "Customer ID: " + customerId + "\nType: " + getCustomerType()
          + "\nName: " + getDisplayName() + "\nAddress: " + address
          + "\nPhone: " + phoneNumber + "\nEmail: " + email;
   }

   protected String accountsSummary() {
      StringBuilder sb = new StringBuilder("\nAccounts:");
      if (accounts.isEmpty()) sb.append(" none");
      for (Account a : accounts) sb.append("\n  - ").append(a);
      return sb.toString();
   }

   // Text describing the Customer //
   public String getCustomerDetails() {
      return baseDetails() + accountsSummary();
   }

   // Diagram method: prints the details to the console. //
   public void displayCustomerDetails() {
      System.out.println(getCustomerDetails());
   }

   public String getCustomerId() {
      return customerId;
   }
     
   public String getAddress() { 
      return address; 
   }
   
   public String getPhoneNumber() {
      return phoneNumber;
   }
     
   public String getEmail() { 
      return email; 
   }

   @Override
   public String toString() { 
      return customerId + " - " + getDisplayName(); }
}
