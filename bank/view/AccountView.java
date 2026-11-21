package bank.view;

import bank.controller.AccountController;
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
   private final ComboBox<String> accountBox = new ComboBox<>();
   private final TextArea output = new TextArea();
   private final Spinner<Integer> monthsSpinner = new Spinner<>(1, 12, 1);
   private final AccountController accounts;

   public AccountView(AccountController accounts) {
      this.accounts = accounts;
      output.setEditable(false);
      output.setPrefRowCount(14);
      output.setStyle("-fx-font-family: monospace;");

      Button detailsButton = new Button("Show details");
      detailsButton.setOnAction(e -> {
         try {
            output.setText(accounts.getAccountDetails(accountBox.getValue()));
         } catch (RuntimeException ex) {
            output.setText(ex.getMessage());
         }
      });

      Button statementButton = new Button("Show statement");
      statementButton.setOnAction(e -> {
         try {
            output.setText(accounts.getStatement(accountBox.getValue(), monthsSpinner.getValue()));
         } catch (RuntimeException ex) {
            output.setText(ex.getMessage());
         }
      });

      root.setPadding(new Insets(20));
      root.getChildren().addAll(new Label("Account number"), accountBox,
            new HBox(10, detailsButton, new Label("Months:"), monthsSpinner, statementButton), output);
   }

   public void refresh() {
      accountBox.getItems().setAll(accounts.getAccountNumbers());
      output.clear();
   }

   public VBox getRoot() {
      return root;
   }
}