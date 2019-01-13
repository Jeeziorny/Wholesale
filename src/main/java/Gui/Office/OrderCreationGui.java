package Gui.Office;

import Database.DaoInterface.*;
import Database.DataAccessObject.*;
import Database.DataAccessObject.DaoChipboard;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Chipboard;
import models.Customer;
import models.Order;
import models.OrderItem;
import models.enums.OrderStatus;
import models.enums.PaymentStatus;

public class OrderCreationGui implements Observable {
  private InvalidationListener listener;

  private DaoChipboard daoChipboard = DaoChipboard.getInstance();
  private IDaoOrder daoOrder = DaoOrder.getInstance();
  private IDaoOrderItem daoOrderItem = DaoOrderItem.getInstance();

  private TableView Chipboards;
  private TableView orderTable;

  private TextField currentChipboardId;
  private TextField currentQuantity;

  private Button addPosition;
  private Button addOrder;

  private Label message;

  private HBox hBox = new HBox();

  private Stage stage;

  private Chipboard currentChipboard;
  private Customer currentCustomer;

  public OrderCreationGui(Customer customer) {
    this.currentCustomer = customer;
    setControls();
    setChipboardsTable();
    setOrderTable();
    setLayout();
    this.stage = new Stage();
    stage.setTitle("Wholesale: Order constructor");
    this.stage.setScene(new Scene(hBox));
    this.stage.alwaysOnTopProperty();
    stage.setOnCloseRequest(e -> listener.invalidated(this));
  }

  public void launch() {
    this.stage.show();
    this.stage.setResizable(false);
  }

  private void setLayout() {
    this.hBox.setPadding(new Insets(30));
    this.hBox.setSpacing(30);
    this.hBox.setPrefWidth(400);
    HBox orderItemBox = new HBox(
            new Label("id: "), this.currentChipboardId,
            new Label("q: "), this.currentQuantity,
            this.addPosition);
    orderItemBox.setSpacing(10);
    orderItemBox.setAlignment(Pos.CENTER);
    VBox rightPanel = new VBox(new HBox(this.message), orderItemBox, this.orderTable, this.addOrder);
    rightPanel.setSpacing(10);
    rightPanel.setAlignment(Pos.CENTER);
    rightPanel.setMinWidth(440);
    this.Chipboards.setMinWidth(200);
    hBox.getChildren().addAll(this.Chipboards, rightPanel);
  }

  private void setControls() {
    this.currentChipboardId = new TextField();
    this.currentChipboardId.setPromptText("id");
    this.currentQuantity = new TextField();
    this.currentQuantity.setPromptText("quantity");
    this.addPosition = new Button("Add");
    this.addPosition.setOnMousePressed(e -> addOrderItem());
    this.addOrder = new Button("New Order");
    this.addOrder.setOnMousePressed(e -> addNewOrder());
    this.message = new Label();
  }

  private void addNewOrder() {
    Order order = new Order();
    order.setCustomerId(this.currentCustomer.getId());
    order.setOrderStatus(OrderStatus.SUSPENDED);
    order.setPaymentStatus(PaymentStatus.PENDING);
    this.daoOrder.insert(order);

    for (Object o : orderTable.getItems()) {
      OrderItem item = (OrderItem) o;
      item.setOrderId(order.getId());
      this.daoOrderItem.insert(item);
    }
    this.stage.close();
    this.listener.invalidated(this);
  }

  private void setOrderTable() {
    this.orderTable = new TableView();
    this.orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn idCol = new TableColumn("Id");
    TableColumn quantityCol = new TableColumn("Quantity");
    TableColumn priceCol = new TableColumn("Price");

    idCol.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("chipboardId"));
    quantityCol.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("quantity"));
    priceCol.setCellValueFactory(new PropertyValueFactory<OrderItem, Double>("price"));

    this.orderTable.getColumns().setAll(idCol, quantityCol, priceCol);
  }

  private void addOrderItem() {
    ObservableList<OrderItem> orderItemList = FXCollections.observableArrayList();
    orderItemList.addAll(orderTable.getItems());
    OrderItem orderItem = new OrderItem();
    try {
      orderItem.setQuantity(Integer.parseInt(this.currentQuantity.getText()));
      orderItem.setChipboardId(Integer.parseInt(this.currentChipboardId.getText()));
      orderItem.setOwner(this.currentCustomer);
      if (orderItem.getQuantity() > 0 && orderItem.getChipboardId() > 0) {
        orderItemList.add(orderItem);
        this.orderTable.setItems(orderItemList);
      }
    } catch (NumberFormatException e) {
      this.message.setText("Incorrect data");
    }
    if (!orderTable.getItems().isEmpty()) {
      this.addOrder.setDisable(false);
    } else {
      this.addOrder.setDisable(true);
    }
  }

  private void setChipboardsTable() {
    this.Chipboards = new TableView();
    this.Chipboards.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn idCol = new TableColumn("Id");
    TableColumn costCol = new TableColumn("Cost");

    idCol.setCellValueFactory(new PropertyValueFactory<Chipboard, Integer>("id"));
    costCol.setCellValueFactory(new PropertyValueFactory<Chipboard, Double>("cost"));

    updateChipboardsTable();

    this.Chipboards.getColumns().setAll(idCol, costCol);
    this.Chipboards.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        this.currentChipboard = (Chipboard) newSelection;
        updateTextFields();
      }
    });
  }

  private void updateChipboardsTable() {
    ObservableList<Chipboard> ChipboardsList = FXCollections.observableArrayList();
    for (Object o : daoChipboard.select()) {
      Chipboard obj = (Chipboard) o;
      ChipboardsList.add(obj);
    }
    this.Chipboards.setItems(ChipboardsList);
  }

  private void updateTextFields() {
    this.currentChipboardId.setText(currentChipboard.getId()+"");
    this.currentQuantity.setText("0");
  }

  @Override
  public void addListener(InvalidationListener listener) {
    this.listener = listener;
  }

  @Override
  public void removeListener(InvalidationListener listener) {

  }
}