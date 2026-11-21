package bank.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** One line on an account statement. */
public class Transaction {
   private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
   private final LocalDateTime time;
   private final String type;
   private final double amount;
   private final double balanceAfter;

   public Transaction(String type, double amount, double balanceAfter) {
      this(LocalDateTime.now(), type, amount, balanceAfter);
   }

   // Used when loading a saved transaction from the database. //
   public Transaction(LocalDateTime time, String type, double amount, double balanceAfter) {
      this.time = time;
      this.type = type;
      this.amount = amount;
      this.balanceAfter = balanceAfter;
   }

   public LocalDateTime getTime() {
      return time;
   }

   public String getType() {
      return type;
   }

   public double getAmount() {
      return amount;
   }

   public double getBalanceAfter() {
      return balanceAfter;
   }

   public String getDetails() {
      return "Date/Time: " + time.format(FMT) + "\nType: " + type
          + String.format("\nAmount: BWP %.2f\nBalance after: BWP %.2f", amount, balanceAfter);
   }

   @Override
   public String toString() {
      return String.format("%s | %-16s | %10.2f | Balance: %10.2f",
             time.format(FMT), type, amount, balanceAfter);
   }
}