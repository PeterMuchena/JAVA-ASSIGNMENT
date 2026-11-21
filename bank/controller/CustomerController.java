package bank.controller;
import bank.model.Bank;
import bank.model.CompanyCustomer;
import bank.model.Customer;
import bank.model.IndividualCustomer;
import java.util.Date;

public class CustomerController {
   private final Bank bank;

   public CustomerController(Bank bank) {
      this.bank = bank;
   }

   public IndividualCustomer registerIndividual(String firstname, String surname, String nationalId,
         Date dateOfBirth, String address, String phone, String email, String password, boolean employed) {
      requireText(firstname, "First name");
      requireText(surname, "Surname");
      requireText(nationalId, "National ID");
      requireText(email, "Email");
      requireText(password, "Password");
      checkEmailFree(email);
      IndividualCustomer c = new IndividualCustomer(firstname.trim(), surname.trim(), nationalId.trim(),
            dateOfBirth, address, phone, email.trim(), password, employed);
      bank.addCustomer(c);
      return c;
   }

   public CompanyCustomer registerCompany(String companyName, String registrationNumber, String companyType,
         String contactPerson, String address, String phone, String email, String password) {
      requireText(companyName, "Company name");
      requireText(registrationNumber, "Registration number");
      requireText(email, "Email");
      requireText(password, "Password");
      checkEmailFree(email);
      CompanyCustomer c = new CompanyCustomer(companyName.trim(), registrationNumber.trim(), companyType,
            contactPerson, address, phone, email.trim(), password);
      bank.addCustomer(c);
      return c;
   }

   public String getCustomerDetails(Customer customer) {
      return customer.getCustomerDetails();
   }

   private void checkEmailFree(String email) {
      if (bank.findCustomerByEmail(email.trim()) != null)
         throw new IllegalArgumentException("That email is already registered");
   }

   private void requireText(String value, String field) {
      if (value == null || value.trim().isEmpty())
         throw new IllegalArgumentException(field + " is required");
   }
}