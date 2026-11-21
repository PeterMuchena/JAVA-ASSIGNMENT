package bank;

import bank.dao.DatabaseManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbCheck {
   public static void main(String[] args) throws SQLException {
      DatabaseManager.initialise();
      try (Connection c = DatabaseManager.getConnection();
           Statement s = c.createStatement();
           ResultSet rs = s.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES "
                 + "WHERE TABLE_SCHEMA = 'PUBLIC' ORDER BY TABLE_NAME")) {
         System.out.println("Tables in the database:");
         while (rs.next()) {
            System.out.println("  " + rs.getString(1));
         }
      }
   }
}
