import Database.HibernateUtil;
import Gui.Office.OrderCreationGui;
import WholesaleException.IncorrectUserDataException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Customer;
import models.User.User;
import models.enums.UserEnum;
import org.hibernate.Hibernate;

import java.util.Optional;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Login Dialog");
    dialog.setHeaderText("Login to the system");

    ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField username = new TextField();
    username.setPromptText("Username");
    PasswordField password = new PasswordField();
    password.setPromptText("Password");

    grid.add(new Label("Username:"), 0, 1);
    grid.add(username, 1, 1);
    grid.add(new Label("Password:"), 0, 2);

    Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
    loginButton.setDisable(true);

    grid.add(password, 1, 2);

    username.textProperty().addListener((observable, oldValue, newValue) -> {
      loginButton.setDisable(newValue.trim().isEmpty());
    });

    dialog.getDialogPane().setContent(grid);

    Platform.runLater(() -> username.requestFocus());

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == loginButtonType) {
        return new Pair<>(username.getText(), password.getText());
      }
      return null;
    });

    Optional<Pair<String, String>> result = dialog.showAndWait();

    result.ifPresent(usernamePassword -> {
      User user = new User(usernamePassword.getValue(), usernamePassword.getKey());
      try {
        HibernateUtil.build(user);
      } catch (IncorrectUserDataException e) {
        grid.add(new Label("Incorrect login details"), 0, 0);
        username.clear();
        password.clear();
        dialog.showAndWait();
      }
    });
  }
}
