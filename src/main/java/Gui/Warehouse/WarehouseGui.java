package Gui.Warehouse;

import Database.DaoInterface.DaoOrderInterface;
import Database.DaoInterface.DaoOrderItemIntreface;
import Database.DataAccessObject.DaoOrder;
import Database.DataAccessObject.DaoOrderItem;
import javafx.application.Platform;
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
import models.Order;
import models.OrderItem;
import models.enums.OrderStatus;

public class WarehouseGui {
  private static WarehouseGui instance;
  private DaoOrderInterface daoOrder = DaoOrder.getInstance();
  private DaoOrderItemIntreface daoOrderItem = DaoOrderItem.getInstance();

  private Stage stage;

  private HBox hBox = new HBox();

  private TableView Orders;
  private TableView OrderSpecification;

  private Button done;
  private Button lackOfMaterials;
  private Button construct;
  private Button issue;

  private Label label;

  private Order currentOrder;

  public static WarehouseGui getInstance() {
    if (instance == null) {
      instance = new WarehouseGui();
    }
    return instance;
  }

  public static void launch() {
    instance.getStage().show();
  }

  private WarehouseGui() {
    setOrderTable();
    setOrderSpecificationTable();
    setButtons();
    setLayout();
    this.stage = new Stage();
    this.stage.setTitle("Wholesale: Warehousman");
    this.stage.setScene(new Scene(this.hBox));
    setState(OrderStatus.PENDING);
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

  private void setOrderTable() {
    this.Orders = new TableView();
    this.Orders.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn idCol = new TableColumn("Id");
    TableColumn customerIdCol = new TableColumn("Customer Id");
    TableColumn OrderStatusCol = new TableColumn("Order Status");

    idCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
    customerIdCol. setCellValueFactory(new PropertyValueFactory<Order, Integer>("customerId"));
    OrderStatusCol.setCellValueFactory(new PropertyValueFactory<Order, OrderStatus>("orderStatus"));

    updateOrderTable();

    this.Orders.getColumns().setAll(idCol, customerIdCol, OrderStatusCol);
    this.Orders.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        this.currentOrder = (Order) newSelection;
        updateOrderSpecificationTable();
      }
    });
    this.hBox.getChildren().add(Orders);
  }

  private void updateOrderTable() {
    ObservableList<Order> orders = FXCollections.observableArrayList();
    for (Object o : daoOrder.select(daoOrder.selectByOrderStatus, OrderStatus.PENDING)) {
      Order obj = (Order) o;
      orders.add(obj);
    }
    Orders.setItems(orders);
  }

  private void setOrderSpecificationTable() {
    this.OrderSpecification = new TableView();
    this.OrderSpecification.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn chipboardIdCol = new TableColumn("Chipboard Id");
    TableColumn quantityCol = new TableColumn("Quantity");

    chipboardIdCol.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("chipboardId"));
    quantityCol.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("quantity"));
    this.OrderSpecification.getColumns().setAll(chipboardIdCol, quantityCol);

    this.hBox.getChildren().add(OrderSpecification);
  }

  private void updateOrderSpecificationTable() {
    ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();
    for (Object o : daoOrderItem.select(daoOrderItem.selectByOrderId, this.currentOrder.getId())) {
      OrderItem obj = (OrderItem) o;
      orderItems.add(obj);
    }
    OrderSpecification.setItems(orderItems);

  }

  private void setButtons() {
    this.done = new Button("Done");
    this.done.setPrefWidth(105);
    this.lackOfMaterials = new Button("Lack of materials");
    this.lackOfMaterials.setPrefWidth(105);
    this.construct = new Button("Construct");
    this.construct.setPrefWidth(105);
    this.issue = new Button("Issue");
    this.issue.setPrefWidth(105);
    this.label = new Label();
    activateButtons();
    VBox vBox = new VBox(new VBox(this.label), construct, lackOfMaterials, done, issue);
    vBox.setSpacing(10);
    vBox.setAlignment(Pos.CENTER);
    this.hBox.getChildren().add(vBox);
  }

  private void activateButtons() {
    this.construct.setOnMousePressed(e -> {
      daoOrder.update(daoOrder.updateOrderStatusById, OrderStatus.CONSTRUCTION, this.currentOrder.getId());
      updateOrderTable();
      issue.setDisable(true);
      setState(OrderStatus.CONSTRUCTION);
    });

    this.done.setOnMousePressed(e -> {
     daoOrder.update(daoOrder.updateOrderStatusById, OrderStatus.DONE, this.currentOrder.getId());
     updateOrderTable();
     OrderSpecification.getItems().clear();
     setState(OrderStatus.DONE);
    });

    this.lackOfMaterials.setOnMousePressed(e -> {
      daoOrder.update(daoOrder.updateOrderStatusById, OrderStatus.LACK_OF_MATERIALS, this.currentOrder.getId());
      updateOrderTable();
      OrderSpecification.getItems().clear();
      setState(OrderStatus.PENDING);
    });
    this.issue.setOnMousePressed(e -> {
      daoOrder.update(daoOrder.updateOrderStatusById, OrderStatus.ISSUED, this.currentOrder.getId());
      updateOrderTable();
      OrderSpecification.getItems().clear();
      setState(OrderStatus.PENDING);
    });
  }

  private void setState(OrderStatus mode) {
    switch(mode) {
      case DONE:  this.done.setDisable(true);
                  this.lackOfMaterials.setDisable(true);
                  this.construct.setDisable(true);
                  this.issue.setDisable(false);
                  setLabel(currentOrder.getId()+": to be issued");
                  Orders.getItems().clear();
                  break;
      case CONSTRUCTION: this.done.setDisable(false);
                         this.lackOfMaterials.setDisable(false);
                         this.construct.setDisable(true);
                         this.issue.setDisable(true);
                         setLabel(currentOrder.getId()+": construction");
                         Orders.getItems().clear();
                         break;
      case PENDING: this.done.setDisable(true);
                    this.lackOfMaterials.setDisable(true);
                    this.construct.setDisable(false);
                    this.issue.setDisable(true);
                    setLabel("");
                    updateOrderTable();
                    Orders.getSelectionModel().selectFirst();
                    break;
    }
  }
}
