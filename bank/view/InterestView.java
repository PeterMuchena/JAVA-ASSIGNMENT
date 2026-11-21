package bank.view;

import bank.controller.AccountController;
import bank.controller.InterestController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.function.Supplier;

public class InterestView {
   private final VBox root = new VBox(10);
   private final ComboBox<String> accountBox = new ComboBox<>();
   private final Label message = new Label();
   private final AccountController accounts;

   public InterestView(InterestController interest, AccountController accounts) {
      this.accounts = accounts;

      Button calcButton = new Button("Calculate interest");
      calcButton.setOnAction(e -> show(() -> interest.calculateInterest(accountBox.getValue())));

      Button applyButton = new Button("Apply interest");
      applyButton.setOnAction(e -> show(() -> interest.applyInterest(accountBox.getValue())));

      Button applyAllButton = new Button("Apply to all accounts");
      applyAllButton.setOnAction(e ->
            show(() -> "Interest applied to " + interest.applyInterestToAll() + " account(s)."));

      root.setPadding(new Insets(20));
      root.getChildren().addAll(new Label("Account number"), accountBox,
            new HBox(10, calcButton, applyButton), applyAllButton, message);
   }

   // shows the controller's answer, or its error message //
   private void show(Supplier<String> action) {
      try {
         message.setStyle("-fx-text-fill: #1e8449;");
         message.setText(action.get());
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