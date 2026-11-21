package bank.view;

import bank.controller.AccountController;
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

   public OpenAccountView(AccountController accounts) {
      typeBox.getItems().addAll("Savings", "Investment", "Cheque");
      typeBox.setValue("Savings");
      branchField.setPromptText("Branch");
      depositField.setPromptText("Opening deposit (BWP)");
      companyNameField.setPromptText("Employer name");
      companyAddressField.setPromptText("Employer address");

      // only show the employer fields for a cheque account //
      setEmployerVisible(false);
      typeBox.setOnAction(e -> setEmployerVisible("Cheque".equals(typeBox.getValue())));

      Button openButton = new Button("Open account");
      openButton.setOnAction(e -> {
         try {
            String result = accounts.openAccount(typeBox.getValue(), branchField.getText(),
                  depositField.getText(), companyNameField.getText(), companyAddressField.getText());
            message.setStyle("-fx-text-fill: #1e8449;");
            message.setText(result);
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

   private void setEmployerVisible(boolean visible) {
      companyNameField.setVisible(visible);
      companyNameField.setManaged(visible);
      companyAddressField.setVisible(visible);
      companyAddressField.setManaged(visible);
   }

   public void refresh() {
      message.setText("");
   }

   public VBox getRoot() {
      return root;
   }
}