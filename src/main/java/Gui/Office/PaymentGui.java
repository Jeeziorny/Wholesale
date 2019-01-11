package Gui.Office;

import Database.DaoInterface.DaoOrderInterface;
import Database.DaoInterface.DaoOrderItemIntreface;
import Database.DataAccessObject.DaoOrder;
import Database.DataAccessObject.DaoOrderItem;
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
import models.Order;
import models.enums.PaymentStatus;

import java.util.List;

public class PaymentGui implements Observable {
  InvalidationListener listener;
  private DaoOrderInterface daoOrder = DaoOrder.getInstance();

  private Stage stage;

  private Customer currentCustomer;

  private HBox hBox = new HBox();

  private Button payButton;

  private TableView customerOrders;

  private Order currentOrder;

  public PaymentGui(Customer customer) {
    this.currentCustomer = customer;
    setCustomerOrdersTable();
    setButtons();
    setLayout();
    this.stage = new Stage();
    stage.setTitle("Wholesale: Payment");
    this.stage.setScene(new Scene(hBox));
    this.stage.setOnCloseRequest(e -> listener.invalidated(this));
  }

  public void launch() {
    this.stage.show();
  }

  private void setLayout() {
    this.hBox.setPadding(new Insets(30));
    this.hBox.setSpacing(30);
    this.hBox.setPrefWidth(450);
    VBox rightPanel = new VBox(this.payButton);
    rightPanel.setAlignment(Pos.CENTER);
    this.customerOrders.setMinWidth(200);
    hBox.getChildren().addAll(this.customerOrders, rightPanel);
  }

  private void setButtons() {
    this.payButton = new Button("Accept Payment");
    this.payButton.setOnMousePressed(e -> Pay());
    this.payButton.setDisable(true);
  }

  private void Pay() {
    daoOrder.update(daoOrder.updatePaymentStatusById,
            PaymentStatus.DONE,
            currentOrder.getId());
    updateCustomerOrdersTable();
    this.stage.close();
    this.listener.invalidated(this);
  }

  private void setCustomerOrdersTable() {
    this.customerOrders = new TableView();
    this.customerOrders.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn idCol = new TableColumn("Id");
    TableColumn statusCol = new TableColumn("Payment Status");
    TableColumn priceCol = new TableColumn("Price");

    idCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
    statusCol.setCellValueFactory(new PropertyValueFactory<Order, PaymentStatus>("paymentStatus"));
    priceCol.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));

    updateCustomerOrdersTable();

    this.customerOrders.getColumns().setAll(idCol, statusCol, priceCol);
    this.customerOrders.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        this.currentOrder = (Order) newSelection;
        if (this.currentOrder.getPaymentStatus().equals(PaymentStatus.PENDING)) {
          this.payButton.setDisable(false);
        }
      }
    });
  }

  private void updateCustomerOrdersTable() {
    ObservableList<Order> orderList = FXCollections.observableArrayList();
    List items = daoOrder
            .select(currentCustomer.getId());
    for (Object o : items) {
      Order obj = (Order) o;
      orderList.add(obj);
    }
    this.customerOrders.setItems(orderList);
  }

  @Override
  public void addListener(InvalidationListener listener) {
    this.listener = listener;
  }

  @Override
  public void removeListener(InvalidationListener listener) {
  }
}
