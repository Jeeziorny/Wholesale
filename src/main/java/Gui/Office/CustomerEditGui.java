package Gui.Office;

import Database.DaoInterface.DaoCustomerInterface;
import Database.DataAccessObject.DaoCustomers;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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

  public CustomerEditGui(Customer customer) {
    this.currentCustomer = customer;
    setControls();
    setLayout();
    this.stage = new Stage();
    stage.setTitle("Wholesale: Edit a customer");
    this.stage.setScene(new Scene(vBox));
    this.stage.setOnCloseRequest(e -> listener.invalidated(this));
  }

  private void setControls() {
    nipField = new TextField();
    nipField.setText(currentCustomer.getNip()+" ");
    nameField = new TextField();
    nameField.setText(currentCustomer.getName());
    confirmButton = new Button("OK");
    confirmButton.setPrefWidth(80);
    confirmButton.setOnMousePressed(e -> {
      try {
        Integer.parseInt(nipField.getText());
        daoCustomer.update(daoCustomer.updateNipById,
                Integer.parseInt(nipField.getText()),
                currentCustomer.getId());
        daoCustomer.update(daoCustomer.updateNameById,
                nameField.getText(),
                currentCustomer.getId());
      } catch (NumberFormatException f) {
        setLog("Incorrect nip");
      }
    });
  }

  private void setLog(String arg) {
    //TODO:
    /*
    Skonczyles tu. Wystarczy, ze wypelnisz ta funkcje i ustawisz layout.
    Trzeba jeszcze powiadomic listenera o UPDATE i o zamknieciu okna.
     */
  }

  private void setLayout() {
    Label description = new Label(
            "Customer id: "+currentCustomer.getId() +
                    "\nCustomer NIP: "+currentCustomer.getNip() +
                    "\nCustomer name: "+currentCustomer.getName() +
                    "\nCustomer discount: "+currentCustomer.getName());
    HBox nameBox = new HBox(new Label("New name: "), this.nameField);
    HBox nipBox = new HBox(new Label("New nip: ", this.nipField));
    VBox buttonBox = new VBox(this.confirmButton);
    buttonBox.setAlignment(Pos.CENTER_RIGHT);
  }

  @Override
  public void addListener(InvalidationListener listener) {
    this.listener = listener;
  }

  @Override
  public void removeListener(InvalidationListener listener) {

  }
}
