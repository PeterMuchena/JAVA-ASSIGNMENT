package bank.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Opens the H2 database and creates the tables from db/schema.sql. //
public class DatabaseManager {
   private static final String URL = "jdbc:h2:./data/bankdb";
   private static final String USER = "sa";
   private static final String PASSWORD = "";

   public static Connection getConnection() throws SQLException {
      return DriverManager.getConnection(URL, USER, PASSWORD);
   }

   public static void initialise() {
      try (Connection c = getConnection(); Statement s = c.createStatement()) {
         String sql = Files.readString(Path.of("db/schema.sql"));
         for (String part : sql.split(";")) {
            if (!part.trim().isEmpty()) {
               s.execute(part);
            }
         }
      } catch (SQLException | IOException e) {
         throw new RuntimeException("Could not set up the database: " + e.getMessage(), e);
      }
   }
}
