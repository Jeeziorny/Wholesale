package Gui.Ceo.Tabs;

import Database.DBSecurity;
import Gui.Office.OfficeGui;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OtherTab extends Tab {
  private DBSecurity handler = DBSecurity.getInstance();

  private Button backupButton = new Button("Backup");
  private Button restoreButton = new Button("Restore");
  private Button officeButton = new Button("Office");

  private TextField backupField = new TextField();
  private TextField restoreField = new TextField();

  public OtherTab() {
    super("Others");
    setLayout();
    officeButton.setOnMousePressed(e -> launchOffice());
    backupButton.setOnMousePressed(e -> backup());
    restoreButton.setOnMousePressed(e -> restore());
    this.setClosable(false);
  }

  private void restore() {

  }

  private void backup() {
    String name = this.backupField.getText();
    if (name.equals("")) {
      name = "someBackup";
    }
    handler.backup(name+".sql");
    backupField.clear();
  }

  private void launchOffice() {
    OfficeGui gui = OfficeGui.getInstance();
    gui.launch();
  }

  private void setLayout() {
    VBox vBox = new VBox();
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(20);
    vBox.setPadding(new Insets(20));

    backupButton.setPrefWidth(100);
    Label backupLabel = new Label("Backup file name: ");
    backupLabel.setPrefWidth(100);
    HBox bBox = new HBox();
    bBox.setSpacing(15);
    bBox.getChildren().addAll(backupLabel, backupField, backupButton);
    bBox.setAlignment(Pos.CENTER);

    restoreButton.setPrefWidth(100);
    Label restoreLabel = new Label("Restore from file: ");
    restoreLabel.setPrefWidth(100);
    HBox rBox = new HBox();
    rBox.setSpacing(15);
    rBox.getChildren().addAll(restoreLabel, restoreField, restoreButton);
    rBox.setAlignment(Pos.CENTER);

    officeButton.setPrefWidth(100);
    Label empty = new Label("");
    empty.setPrefWidth(278);
    HBox oBox = new HBox();
    oBox.getChildren().addAll(empty, officeButton);
    oBox.setAlignment(Pos.CENTER);
    vBox.getChildren().addAll(oBox, bBox, rBox);
    this.setContent(vBox);
  }
}
