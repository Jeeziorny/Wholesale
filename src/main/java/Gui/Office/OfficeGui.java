package Gui.Office;

import Database.DaoInterface.*;
import Database.DataAccessObject.*;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Customer;

public class OfficeGui implements InvalidationListener {
  private static OfficeGui instance;

  private DaoCustomerInterface daoCustomer = DaoCustomers.getInstance();

  private Stage stage;

  private HBox hBox = new HBox();

  private TableView Customers;

  private Button newOrder;
  private Button newCustomer;
  private Button editCustomer;
  private Button payment;

  private Label label;

  private Customer currentCustomer;

  public static OfficeGui getInstance() {
    if (instance == null) {
      instance = new OfficeGui();
    }
    return instance;
  }

  public static void launch() {
    instance.getStage().show();
    instance.getStage().setResizable(false);
  }

  private OfficeGui() {
    setCustomerTable();
    setButtons();
    setLayout();
    this.stage = new Stage();
    stage.setTitle("Wholesale: Office");
    this.stage.setScene(new Scene(hBox));
  }

  private Stage getStage() {
    return this.stage;
  }

  private void setLayout() {
    this.hBox.setPadding(new Insets(30));
    this.hBox.setSpacing(30);
    this.hBox.setAlignment(Pos.CENTER);
  }

  private void setLabel(String s) {
    this.label.setText(s);
  }

  private void setCustomerTable() {
    Customers = new TableView();
    Customers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn idCol = new TableColumn("Id");
    TableColumn nipCol = new TableColumn("NIP");
    TableColumn nameCol = new TableColumn("Name");
    TableColumn discountCol = new TableColumn("Discount");

    idCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
    nipCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("nip"));
    nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
    discountCol.setCellValueFactory(new PropertyValueFactory<Customer, Double>("discount"));

    updateCustomerTable();

    Customers.getColumns().setAll(idCol, nipCol, nameCol, discountCol);
    Customers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        this.currentCustomer = (Customer) newSelection;
        this.newOrder.setDisable(false);
        this.payment.setDisable(false);
        this.editCustomer.setDisable(false);
        setLabel("Current Customer:\nId: "+currentCustomer.getId());
      }
    });
    hBox.getChildren().add(Customers);
  }

  private void updateCustomerTable() {
    ObservableList<Customer> customersList = FXCollections.observableArrayList();
    for (Object o : daoCustomer.select()) {
      Customer obj = (Customer) o;
      customersList.add(obj);
    }
    this.Customers.setItems(customersList);
  }

  private void setButtons() {
    this.newOrder = new Button("New Order");
    this.newOrder.setPrefWidth(105);
    this.newOrder.setDisable(true);
    this.newCustomer = new Button("New Customer");
    this.newCustomer.setPrefWidth(105);
    this.editCustomer = new Button("Edit Customer");
    this.editCustomer.setPrefWidth(105);
    this.editCustomer.setDisable(true);
    this.payment = new Button("Orders..");
    this.payment.setPrefWidth(105);
    this.payment.setDisable(true);
    this.label = new Label();
    activateButtons();
    VBox labelVBox = new VBox(this.label);
    labelVBox.setAlignment(Pos.CENTER);
    VBox vBox = new VBox(labelVBox, newOrder, newCustomer, editCustomer, payment);
    vBox.setSpacing(10);
    vBox.setAlignment(Pos.CENTER);
    this.hBox.getChildren().add(vBox);
  }

  private void activateButtons() {
    this.newOrder.setOnMousePressed(e -> {
      OrderCreationGui gui = new OrderCreationGui(currentCustomer);
      gui.launch();
      gui.addListener(this);
    });
    this.newCustomer.setOnMousePressed(e -> {
      CustomerEditGui gui = new CustomerEditGui(null);
      gui.launch();
      gui.addListener(this);
    });
    this.editCustomer.setOnMousePressed(e -> {
      CustomerEditGui gui = new CustomerEditGui(currentCustomer);
      gui.launch();
      gui.addListener(this);
    });
    this.payment.setOnMousePressed(e -> {
      OrdersGui gui = new OrdersGui(currentCustomer);
      gui.launch();
      gui.addListener(this);
    });
  }

  @Override
  public void invalidated(Observable observable) {
    updateCustomerTable();
    currentCustomer = null;
    newOrder.setDisable(true);
    payment.setDisable(true);
    editCustomer.setDisable(true);
  }
}
