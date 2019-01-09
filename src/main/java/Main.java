import Database.HibernateUtil;
import Gui.Warehouse.WarehouseGui;
import javafx.application.Application;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;
import models.enums.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main extends Application {
  User user;
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    getUser();
  }

  private void getUser() {
    List<String> choices = new ArrayList<String>();
    choices.add("Warehouse");
    choices.add("Office");
    choices.add("CEO");

    ChoiceDialog<String> dialog = new ChoiceDialog<String>("Warehouse", choices);
    dialog.setTitle("Wholesale app");
    dialog.setHeaderText("Hello, please choose user");
    dialog.setContentText("User");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(name -> setUser(name));
  }

  private void setUser(final String name) {
    if (name.equals("Warehouse")) {
      HibernateUtil.build("warehouseman.cfg.xml");
    }
//    } else if (name.equals("Office")) {
//      this.user = User.OFFICE;
//      OfficeGui.launch();
//    } else if (name.equals("CEO")) {
//      this.user = User.CEO;
//      CeoGui.launch();
//    }
  }
}
