package bank.view;

import bank.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView {
   private final VBox root = new VBox(12);
   private final TextField emailField = new TextField();
   private final PasswordField passwordField = new PasswordField();
   private final Label message = new Label();

   public LoginView(LoginController controller, Runnable onLoggedIn) {
      Label title = new Label("Bank Login");
      title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
      emailField.setPromptText("Email");
      passwordField.setPromptText("Password");
      message.setStyle("-fx-text-fill: #c0392b;");

      Button loginButton = new Button("Log in");
      loginButton.setDefaultButton(true);
      loginButton.setOnAction(e -> {
         try {
            controller.login(emailField.getText(), passwordField.getText());
            message.setText("");
            passwordField.clear();
            onLoggedIn.run();
         } catch (RuntimeException ex) {
            message.setText(ex.getMessage());
         }
      });

      root.setPadding(new Insets(30));
      root.setAlignment(Pos.CENTER);
      root.setMaxWidth(320);
      root.getChildren().addAll(title, emailField, passwordField, loginButton, message);
   }

   public VBox getRoot() {
      return root;
   }
}