package bank.view;

import bank.controller.AccountController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DepositView {
   private final VBox root = new VBox(10);
   private final ComboBox<String> accountBox = new ComboBox<>();
   private final TextField amountField = new TextField();
   private final Label message = new Label();
   private final AccountController accounts;

   public DepositView(AccountController accounts) {
      this.accounts = accounts;
      amountField.setPromptText("Amount (BWP)");

      Button depositButton = new Button("Deposit");
      depositButton.setOnAction(e -> run(true));
      Button withdrawButton = new Button("Withdraw");
      withdrawButton.setOnAction(e -> run(false));

      root.setPadding(new Insets(20));
      root.getChildren().addAll(new Label("Account number"), accountBox, amountField,
            new HBox(10, depositButton, withdrawButton), message);
   }

   private void run(boolean isDeposit) {
      try {
         String result = isDeposit
               ? accounts.deposit(accountBox.getValue(), amountField.getText())
               : accounts.withdraw(accountBox.getValue(), amountField.getText());
         message.setStyle("-fx-text-fill: #1e8449;");
         message.setText(result);
         amountField.clear();
      } catch (RuntimeException ex) {
         message.setStyle("-fx-text-fill: #c0392b;");
         message.setText(ex.getMessage());
      }
   }

   public void refresh() {
      accountBox.getItems().setAll(accounts.getAccountNumbers());
      message.setText("");
   }

   public VBox getRoot() {
      return root;
   }
}