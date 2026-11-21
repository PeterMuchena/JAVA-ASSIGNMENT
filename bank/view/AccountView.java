package bank.view;

import bank.controller.AccountController;
import bank.controller.LoginController;
import bank.model.Account;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AccountView {
   private final VBox root = new VBox(10);
   private final ComboBox<Account> accountBox = new ComboBox<>();
   private final TextArea output = new TextArea();
   private final Spinner<Integer> monthsSpinner = new Spinner<>(1, 12, 1);
   private final AccountController accounts;
   private final LoginController login;

   public AccountView(AccountController accounts, LoginController login) {
      this.accounts = accounts;
      this.login = login;
      output.setEditable(false);
      output.setPrefRowCount(14);
      output.setStyle("-fx-font-family: monospace;");

      Button detailsButton = new Button("Show details");
      detailsButton.setOnAction(e -> show(false));
      Button statementButton = new Button("Show statement");
      statementButton.setOnAction(e -> show(true));

      root.setPadding(new Insets(20));
      root.getChildren().addAll(new Label("Account"), accountBox,
            new HBox(10, detailsButton, new Label("Months:"), monthsSpinner, statementButton), output);
   }

   private void show(boolean statement) {
      try {
         Account a = accountBox.getValue();
         if (a == null) {
            throw new IllegalArgumentException("Select an account");
         }
         if (statement) {
            output.setText(accounts.getStatement(a, monthsSpinner.getValue()));
         } else {
            output.setText(accounts.getAccountDetails(a));
         }
      } catch (RuntimeException ex) {
         output.setText(ex.getMessage());
      }
   }

   public void refresh() {
      if (login.getCurrentCustomer() == null) {
         accountBox.getItems().clear();
      } else {
         accountBox.getItems().setAll(accounts.getAccounts(login.getCurrentCustomer()));
      }
      output.clear();
   }

   public VBox getRoot() {
      return root;
   }
}