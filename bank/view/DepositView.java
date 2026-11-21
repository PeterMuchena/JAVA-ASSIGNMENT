package bank.view;

import bank.controller.AccountController;
import bank.controller.LoginController;
import bank.model.Account;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DepositView {
   private final VBox root = new VBox(10);
   private final ComboBox<Account> accountBox = new ComboBox<>();
   private final TextField amountField = new TextField();
   private final Label message = new Label();
   private final AccountController accounts;
   private final LoginController login;

   public DepositView(AccountController accounts, LoginController login) {
      this.accounts = accounts;
      this.login = login;
      amountField.setPromptText("Amount (BWP)");

      Button depositButton = new Button("Deposit");
      depositButton.setOnAction(e -> run(true));
      Button withdrawButton = new Button("Withdraw");
      withdrawButton.setOnAction(e -> run(false));

      root.setPadding(new Insets(20));
      root.getChildren().addAll(new Label("Account"), accountBox, amountField,
            new HBox(10, depositButton, withdrawButton), message);
   }

   private void run(boolean isDeposit) {
      try {
         Account a = accountBox.getValue();
         if (a == null) {
            throw new IllegalArgumentException("Select an account");
         }
         if (isDeposit) {
            accounts.deposit(a, amountField.getText());
         } else {
            accounts.withdraw(a, amountField.getText());
         }
         message.setStyle("-fx-text-fill: #1e8449;");
         message.setText("Done. " + a);
         amountField.clear();
      } catch (RuntimeException ex) {
         message.setStyle("-fx-text-fill: #c0392b;");
         message.setText(ex.getMessage());
      }
   }

   public void refresh() {
      if (login.getCurrentCustomer() == null) {
         accountBox.getItems().clear();
      } else {
         accountBox.getItems().setAll(accounts.getAccounts(login.getCurrentCustomer()));
      }
      message.setText("");
   }

   public VBox getRoot() {
      return root;
   }
}