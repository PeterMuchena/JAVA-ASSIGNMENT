package bank.view;

import bank.controller.AccountController;
import bank.controller.InterestController;
import bank.controller.LoginController;
import bank.model.Account;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InterestView {
   private final VBox root = new VBox(10);
   private final ComboBox<Account> accountBox = new ComboBox<>();
   private final Label message = new Label();
   private final AccountController accounts;
   private final InterestController interest;
   private final LoginController login;

   public InterestView(InterestController interest, AccountController accounts, LoginController login) {
      this.interest = interest;
      this.accounts = accounts;
      this.login = login;

      Button calcButton = new Button("Calculate interest");
      calcButton.setOnAction(e -> {
         try {
            Account a = selected();
            message.setStyle("-fx-text-fill: #1e8449;");
            message.setText(String.format("Interest due: BWP %.2f", interest.calculateInterest(a)));
         } catch (RuntimeException ex) {
            showError(ex);
         }
      });

      Button applyButton = new Button("Apply interest");
      applyButton.setOnAction(e -> {
         try {
            Account a = selected();
            interest.applyInterest(a);
            message.setStyle("-fx-text-fill: #1e8449;");
            message.setText("Interest applied. " + a);
         } catch (RuntimeException ex) {
            showError(ex);
         }
      });

      Button applyAllButton = new Button("Apply to all accounts");
      applyAllButton.setOnAction(e -> {
         try {
            int count = interest.applyInterestToAll();
            message.setStyle("-fx-text-fill: #1e8449;");
            message.setText("Interest applied to " + count + " account(s).");
         } catch (RuntimeException ex) {
            showError(ex);
         }
      });

      root.setPadding(new Insets(20));
      root.getChildren().addAll(new Label("Account"), accountBox,
            new HBox(10, calcButton, applyButton), applyAllButton, message);
   }

   private Account selected() {
      Account a = accountBox.getValue();
      if (a == null) {
         throw new IllegalArgumentException("Select an account");
      }
      return a;
   }

   private void showError(RuntimeException ex) {
      message.setStyle("-fx-text-fill: #c0392b;");
      message.setText(ex.getMessage());
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