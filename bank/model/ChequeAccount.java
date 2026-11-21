package bank.model;
public class ChequeAccount extends Account implements WithdrawFunction {
   private final String companyName;     // the employer
   private final String companyAddress;

   public ChequeAccount(Customer customer, String branch, double openingDeposit,
                        String companyName, String companyAddress) {
      super(customer, branch);
      if (companyName == null || companyName.trim().isEmpty())
         throw new IllegalArgumentException("Employer (company) name is required for a cheque account");
      this.companyName = companyName.trim();
      this.companyAddress = companyAddress == null ? "" : companyAddress.trim();
      if (openingDeposit > 0) deposit(openingDeposit, "Opening deposit");
   }

   // Used when loading from the database: keeps the saved number, balance and status. //
   public ChequeAccount(Customer customer, String branch, String accountNumber, double balance,
                        Status status, String companyName, String companyAddress) {
      super(customer, branch, accountNumber, balance, status);
      this.companyName = companyName;
      this.companyAddress = companyAddress == null ? "" : companyAddress;
   }

   @Override public String getAccountType() { 
      return "Cheque"; }

   @Override
   public void withdraw(double amount) {
      debit(amount);
   }

   /** OVERRIDING: adds the employer details. */
   @Override
   public String getAccountDetails() {
      return super.getAccountDetails() + "\nEmployer: " + companyName
          + (companyAddress.isEmpty() ? "" : ", " + companyAddress);
   }

   public String getCompanyName() { 
      return companyName; }
   public String getCompanyAddress() { 
      return companyAddress; }
}
