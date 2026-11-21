package bank.model;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IndividualCustomer extends Customer {
   private final String firstname;
   private final String surname;
   private final String nationalID;
   private final Date dateOfBirth;
   private final boolean employed;   // needed to decide cheque-account eligibility

   public IndividualCustomer(String firstname, String surname, String nationalID, Date dateOfBirth,
                             String address, String phoneNumber, String email, String password,
                             boolean employed) {
      super(address, phoneNumber, email, password);
      this.firstname = firstname;
      this.surname = surname;
      this.nationalID = nationalID;
      this.dateOfBirth = dateOfBirth;
      this.employed = employed;
   }

   // Used when loading from the database: keeps the saved customer ID. //
   public IndividualCustomer(String customerId, String firstname, String surname, String nationalID,
                             Date dateOfBirth, String address, String phoneNumber, String email,
                             String password, boolean employed) {
      super(customerId, address, phoneNumber, email, password);
      this.firstname = firstname;
      this.surname = surname;
      this.nationalID = nationalID;
      this.dateOfBirth = dateOfBirth;
      this.employed = employed;
   }

   @Override public String getCustomerType() {
      return "Individual";
   }

   @Override public String getDisplayName() {
      return (firstname + " " + surname).trim();
   }

   @Override public boolean isEligibleForCheque() {
      return employed;
   }

   public String getFirstname() {
      return firstname;
   }

   public String getSurname() {
      return surname;
   }

   public String getNationalID() {
      return nationalID;
   }

   public Date getDateOfBirth() {
      return dateOfBirth;
   }

   public boolean isEmployed() {
      return employed;
   }

   @Override
   public String getCustomerDetails() {
      String dob = dateOfBirth == null ? "-" : new SimpleDateFormat("dd/MM/yyyy").format(dateOfBirth);
      return baseDetails() + "\nNational ID: " + nationalID + "\nDate of birth: " + dob
          + "\nEmployed: " + (employed ? "Yes" : "No") + accountsSummary();
   }

   @Override
   public void displayCustomerDetails() {
      System.out.println("--- Individual customer ---");
      super.displayCustomerDetails();
   }
}