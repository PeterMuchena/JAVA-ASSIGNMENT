package bank.model;
public class SavingsAccount extends Account implements InterestCalculation {
   private final double interestRate = 0.0005; // 0.05%

   public SavingsAccount(Customer customer, String branch, double openingDeposit) {
      super(customer, branch);
      if (openingDeposit > 0) deposit(openingDeposit, "Opening deposit");
   }

   @Override public String getAccountType() { 
      return "Savings";
   }

   @Override
   public double calculateInterest() {
      return getBalance() * interestRate;
   }

   @Override
   public void applyInterest() {
      if (getStatus() != Status.ACTIVE) 
         return;
      double interest = calculateInterest();
      if (interest > 0) deposit(interest, "Interest");
   }

   // Used when loading from the database: keeps the saved number, balance and status. //
   public SavingsAccount(Customer customer, String branch, String accountNumber,
                         double balance, Status status) {
      super(customer, branch, accountNumber, balance, status);
   }

   public double getInterestRate() {
      return interestRate;
   }

}

