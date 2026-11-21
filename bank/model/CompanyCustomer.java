package bank.model;

public class CompanyCustomer extends Customer {
   private final String companyName;
   private final String registrationNumber;
   private final String companyType;
   private final String contactPerson;

   public CompanyCustomer(String companyName, String registrationNumber, String companyType,
                          String contactPerson, String address, String phoneNumber,
                          String email, String password) {
      super(address, phoneNumber, email, password);
      this.companyName = companyName;
      this.registrationNumber = registrationNumber;
      this.companyType = companyType;
      this.contactPerson = contactPerson;
   }

   // Used when loading from the database: keeps the saved customer ID. //
   public CompanyCustomer(String customerId, String companyName, String registrationNumber,
                          String companyType, String contactPerson, String address,
                          String phoneNumber, String email, String password) {
      super(customerId, address, phoneNumber, email, password);
      this.companyName = companyName;
      this.registrationNumber = registrationNumber;
      this.companyType = companyType;
      this.contactPerson = contactPerson;
   }

   @Override
   public String getCustomerType() {
      return "Company";
   }

   @Override
   public String getDisplayName() {
      return companyName;
   }

   @Override
   public boolean isEligibleForCheque() {        // companies are not "employed"//
      return false;
   }

   public String getCompanyName() {
      return companyName;
   }

   public String getRegistrationNumber() {
      return registrationNumber;
   }

   public String getCompanyType() {
      return companyType;
   }

   public String getContactPerson() {
      return contactPerson;
   }

   @Override
   public String getCustomerDetails() {
      return baseDetails() + "\nRegistration No: " + registrationNumber + "\nCompany type: " + companyType
          + "\nContact person: " + contactPerson + accountsSummary();
   }

   @Override
   public void displayCustomerDetails() {
      System.out.println("--- Company customer ---");
      super.displayCustomerDetails();
   }
}