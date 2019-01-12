package Gui.Office;

import Database.DaoInterface.DaoCustomerInterface;
import Database.DataAccessObject.DaoCustomers;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Customer;

public class CustomerEditGui implements Observable {
  InvalidationListener listener;

  private DaoCustomerInterface daoCustomer = DaoCustomers.getInstance();

  private Customer currentCustomer;

  private Stage stage;
  private VBox vBox = new VBox();
  private Button confirmButton;
  private TextField nameField;
  private TextField nipField;
  private Label message;

  public CustomerEditGui(Customer customer) {
    this.currentCustomer = customer;
    if (this.currentCustomer == null) {
      this.currentCustomer = new Customer();
      this.currentCustomer.setDiscount(0);
      this.currentCustomer.setName("");
      this.currentCustomer.setNip(0);
    }
    setControls();
    setLayout();
    this.stage = new Stage();
    this.stage.setTitle("Wholesale: Edit a customer");
    this.stage.setScene(new Scene(vBox));
    this.stage.setOnCloseRequest(e -> listener.invalidated(this));
  }

  public void launch() {
    this.stage.show();
  }

  private void setControls() {
    this.message = new Label("Enter data");
    this.nipField = new TextField();
    this.nipField.setText(currentCustomer.getNip()+"");
    this.nameField = new TextField();
    this.nameField.setText(currentCustomer.getName());
    this.confirmButton = new Button("OK");
    this.confirmButton.setPrefWidth(80);
    this.confirmButton.setOnMousePressed(e -> {
      if (currentCustomer.getName().equals("")) {
        insertCustomer();
      } else {
        updateCustomer();
      }
      listener.invalidated(this);
      this.stage.close();
    });
  }

  private void updateCustomer() {
    try {
      Integer.parseInt(this.nipField.getText());
      this.daoCustomer.update(daoCustomer.updateNipById,
              Integer.parseInt(this.nipField.getText()),
              this.currentCustomer.getId());
      this.daoCustomer.update(daoCustomer.updateNameById,
              this.nameField.getText(),
              this.currentCustomer.getId());
    } catch (NumberFormatException f) {
      setLog("Incorrect nip");
    }
  }

  private void insertCustomer() {
    try {
      int nip = Integer.parseInt(this.nipField.getText());
      currentCustomer.setNip(nip);
      currentCustomer.setName(nameField.getText());
      this.daoCustomer.insert(currentCustomer);
    } catch (NumberFormatException e) {
      setLog("Incorrect nip");
    }
  }

  private void setLog(String arg) {
    this.message.setText(arg);
  }

  private void setLayout() {
    this.vBox.setPadding(new Insets(30));
    this.vBox.setSpacing(10);
    Label description = new Label(
            "Customer id: "+currentCustomer.getId() +
                    "\nCustomer NIP: "+currentCustomer.getNip() +
                    "\nCustomer name: "+currentCustomer.getName() +
                    "\nCustomer discount: "+currentCustomer.getDiscount());
    Label newName = new Label("New name: ");
    newName.setPrefWidth(100);
    HBox nameBox = new HBox(newName);
    nameBox.getChildren().add(this.nameField);
    Label newNip = new Label("New nip: ");
    newNip.setPrefWidth(100);
    HBox nipBox = new HBox(newNip);
    nipBox.getChildren().add(this.nipField);
    VBox buttonBox = new VBox(this.confirmButton);
    buttonBox.setAlignment(Pos.CENTER_RIGHT);
    this.vBox.getChildren().addAll(description, this.message, nameBox, nipBox, buttonBox);
  }

  @Override
  public void addListener(InvalidationListener listener) {
    this.listener = listener;
  }

  @Override
  public void removeListener(InvalidationListener listener) {

  }
}
