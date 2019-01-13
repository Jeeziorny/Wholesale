package Gui.Ceo.Tabs;

import Database.DaoInterface.DaoWarehouseInterface;
import Database.DataAccessObject.DaoWarehouse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Warehouse;

public class WarehouseTab extends Tab {
  private DaoWarehouseInterface daoWarehouse = DaoWarehouse.getInstance();

  private TableView warehouseTable;

  private TextField chipboardField;
  private TextField quantityField;
  private Button confirm;
  private Button refresh;
  private Label message;

  private Warehouse currentPosition;

  public WarehouseTab() {
    super("Warehouse");
    currentPosition = new Warehouse();
    setWarehouseTable();
    setControls();
    setLayout();
    this.setClosable(false);
  }

  private void setControls() {
    this.chipboardField = new TextField(currentPosition.getChipboardId()+"");
    this.chipboardField.setPrefWidth(245);
    this.quantityField = new TextField(currentPosition.getQuantity()+"");
    this.quantityField.setPrefWidth(250);
    this.confirm = new Button("Update");
    this.confirm.setPrefWidth(80);
    this.confirm.setOnMousePressed(e -> updateWarehouse());
    this.confirm.setDisable(true);
    this.refresh = new Button("Refresh");
    this.refresh.setPrefWidth(80);
    this.refresh.setOnMousePressed(e -> updateWarehouseTable());
    this.message = new Label();
  }

  private void updateWarehouse() {
    try {
      int q = Integer.parseInt(quantityField.getText());
      daoWarehouse.update(DaoWarehouse.updateQuantityById, q, currentPosition.getChipboardId());
      updateWarehouseTable();
      confirm.setDisable(true);
    } catch (NumberFormatException e) {
      setMessage("Incorrect quantity");
    }
  }

  private void setMessage(String arg) {
    this.message.setText(arg);
  }

  private void setLayout() {
    VBox vBox = new VBox(warehouseTable);
    vBox.setPadding(new Insets(20));
    vBox.setSpacing(10);
    Label idLabel = new Label("id: ");
    idLabel.setPrefWidth(70);
    Label qLabel = new Label("Quantity: ");
    qLabel.setPrefWidth(70);

    VBox idBox = new VBox();
    idBox.setAlignment(Pos.CENTER_LEFT);
    idBox.getChildren().addAll(idLabel, chipboardField);

    VBox qBox = new VBox();
    qBox.setAlignment(Pos.CENTER_LEFT);
    qBox.getChildren().addAll(qLabel, quantityField);

    HBox hBox1 = new HBox(idBox);
    hBox1.setPrefWidth(250);

    HBox hBox2 = new HBox(qBox);
    hBox2.setPrefWidth(250);

    HBox hBox = new HBox(hBox1, hBox2);
    HBox confirmBox = new HBox(confirm, refresh);
    confirmBox.setSpacing(20);
    confirmBox.setAlignment(Pos.CENTER_RIGHT);
    vBox.getChildren().addAll(hBox, confirmBox);
    this.setContent(vBox);
  }

  private void setWarehouseTable() {
    this.warehouseTable = new TableView();
    this.warehouseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn chipboardIdCol = new TableColumn("Id");
    TableColumn quantityCol = new TableColumn("Value");

    chipboardIdCol.setCellValueFactory(new PropertyValueFactory<Warehouse, Integer>("chipboardId"));
    quantityCol.setCellValueFactory(new PropertyValueFactory<Warehouse, Integer>("quantity"));

    updateWarehouseTable();

    warehouseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        this.currentPosition = (Warehouse) newSelection;
        this.confirm.setDisable(false);
        this.chipboardField.setText(currentPosition.getChipboardId()+"");
      }
    });
    this.warehouseTable.getColumns().setAll(chipboardIdCol, quantityCol);
  }

  private void updateWarehouseTable() {
    ObservableList<Warehouse> warehouseList = FXCollections.observableArrayList();
    for (Object o : daoWarehouse.select()) {
      Warehouse obj = (Warehouse) o;
      warehouseList.add(obj);
    }
    this.warehouseTable.setItems(warehouseList);
  }
}
