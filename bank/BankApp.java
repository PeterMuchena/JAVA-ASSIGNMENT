package bank;

import bank.controller.AccountController;
import bank.controller.InterestController;
import bank.controller.LoginController;
import bank.model.Bank;
import bank.model.IndividualCustomer;
import bank.view.AccountView;
import bank.view.DepositView;
import bank.view.InterestView;
import bank.view.LoginView;
import bank.view.OpenAccountView;
import java.util.Date;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BankApp extends Application {
   @Override
   public void start(Stage stage) {
      Bank bank = new Bank();
      bank.addCustomer(new IndividualCustomer("Peter", "Muchena", "123456789", new Date(),
            "Gaborone", "71234567", "peter.muchena@example.com", "12345", true));

      LoginController loginController = new LoginController(bank);
      AccountController accountController = new AccountController(bank);
      InterestController interestController = new InterestController(bank);

      OpenAccountView openView = new OpenAccountView(accountController, loginController);
      DepositView depositView = new DepositView(accountController, loginController);
      AccountView accountView = new AccountView(accountController, loginController);
      InterestView interestView = new InterestView(interestController, accountController, loginController);

      Tab open = new Tab("Open account", openView.getRoot());
      Tab deposit = new Tab("Deposit / Withdraw", depositView.getRoot());
      Tab accounts = new Tab("Accounts", accountView.getRoot());
      Tab interest = new Tab("Interest", interestView.getRoot());
      TabPane tabs = new TabPane(open, deposit, accounts, interest);
      tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

      Runnable refreshAll = () -> {
         openView.refresh();
         depositView.refresh();
         accountView.refresh();
         interestView.refresh();
      };
      tabs.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> refreshAll.run());

      StackPane root = new StackPane();
      BorderPane main = new BorderPane();
      main.setCenter(tabs);

      LoginView loginView = new LoginView(loginController, () -> {
         refreshAll.run();
         root.getChildren().setAll(main);
      });

      Button logoutButton = new Button("Log out");
      logoutButton.setOnAction(e -> {
         loginController.logout();
         root.getChildren().setAll(loginView.getRoot());
      });
      HBox topBar = new HBox(logoutButton);
      topBar.setPadding(new Insets(8));
      main.setTop(topBar);

      root.getChildren().add(loginView.getRoot());
      stage.setTitle("Banking App");
      stage.setScene(new Scene(root, 680, 520));
      stage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }
}