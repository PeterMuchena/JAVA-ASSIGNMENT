package bank.model;
public class InvestmentAccount extends Account implements WithdrawFunction, InterestCalculation {
   private final double interestRate = 0.05;           // 5%
   private final double minimumOpeningDeposit = 500.00;

   public InvestmentAccount(Customer customer, String branch, double openingDeposit) {
      super(customer, branch);
      if (openingDeposit < minimumOpeningDeposit)
         throw new IllegalArgumentException(
            String.format("Investment account needs an opening deposit of at least BWP%.2f", minimumOpeningDeposit));
      deposit(openingDeposit, "Opening deposit");
   }

   @Override public String getAccountType() { 
      return "Investment"; }

   @Override
   public void withdraw(double amount) {
      debit(amount);
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
   public InvestmentAccount(Customer customer, String branch, String accountNumber,
                            double balance, Status status) {
      super(customer, branch, accountNumber, balance, status);
   }

   public double getInterestRate() {
      return interestRate;
   }

   public double getMinimumOpeningDeposit() {
      return minimumOpeningDeposit;
   }

}
