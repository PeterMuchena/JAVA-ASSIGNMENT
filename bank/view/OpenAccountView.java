package bank.view;

import bank.controller.AccountController;
import bank.controller.LoginController;
import bank.model.Customer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class OpenAccountView {
   private final VBox root = new VBox(10);
   private final ComboBox<String> typeBox = new ComboBox<>();
   private final TextField branchField = new TextField();
   private final TextField depositField = new TextField();
   private final TextField companyNameField = new TextField();
   private final TextField companyAddressField = new TextField();
   private final Label message = new Label();

   public OpenAccountView(AccountController accounts, LoginController login) {
      typeBox.getItems().addAll("Savings", "Investment", "Cheque");
      typeBox.setValue("Savings");
      branchField.setPromptText("Branch");
      depositField.setPromptText("Opening deposit (BWP)");
      companyNameField.setPromptText("Employer name");
      companyAddressField.setPromptText("Employer address");

      // only show the employer fields for a cheque account //
      companyNameField.setVisible(false);
      companyNameField.setManaged(false);
      companyAddressField.setVisible(false);
      companyAddressField.setManaged(false);
      typeBox.setOnAction(e -> {
         boolean cheque = "Cheque".equals(typeBox.getValue());
         companyNameField.setVisible(cheque);
         companyNameField.setManaged(cheque);
         companyAddressField.setVisible(cheque);
         companyAddressField.setManaged(cheque);
      });

      Button openButton = new Button("Open account");
      openButton.setOnAction(e -> {
         try {
            Customer c = login.getCurrentCustomer();
            Object opened;
            if ("Savings".equals(typeBox.getValue())) {
               opened = accounts.openSavings(c, branchField.getText(), depositField.getText());
            } else if ("Investment".equals(typeBox.getValue())) {
               opened = accounts.openInvestment(c, branchField.getText(), depositField.getText());
            } else {
               opened = accounts.openCheque(c, branchField.getText(), depositField.getText(),
                     companyNameField.getText(), companyAddressField.getText());
            }
            message.setStyle("-fx-text-fill: #1e8449;");
            message.setText("Opened: " + opened);
            depositField.clear();
         } catch (RuntimeException ex) {
            message.setStyle("-fx-text-fill: #c0392b;");
            message.setText(ex.getMessage());
         }
      });

      root.setPadding(new Insets(20));
      root.getChildren().addAll(new Label("Account type"), typeBox, branchField, depositField,
            companyNameField, companyAddressField, openButton, message);
   }

   public void refresh() {
      message.setText("");
   }

   public VBox getRoot() {
      return root;
   }
}