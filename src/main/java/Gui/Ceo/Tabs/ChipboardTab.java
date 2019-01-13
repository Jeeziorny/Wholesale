package Gui.Ceo.Tabs;

import Database.DaoInterface.DaoChipboardInterface;
import Database.DataAccessObject.DaoChipboard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Chipboard;

public class ChipboardTab extends Tab {
  private TableView chipboardTable;
  private DaoChipboardInterface daoChipboard = DaoChipboard.getInstance();

  private TextField idField;
  private TextField sizeField;
  private TextField costField;

  private Button updateButton;
  private Button insertButton;
  private Button refresh;

  private Label message;

  public ChipboardTab() {
    super("Chipboards");
    setChipboardTable();
    setControls();
    setLayout();
    this.setClosable(false);
  }

  private void setLayout() {
    VBox vBox = new VBox(this.chipboardTable);
    vBox.setSpacing(20);
    vBox.setPadding(new Insets(20));
    HBox textBox = new HBox();
    Label id = new Label("id:");
    id.setPrefWidth(83);
    VBox idBox = new VBox();
    idBox.setAlignment(Pos.CENTER_LEFT);
    idBox.getChildren().addAll(id, this.idField);
    idBox.setPrefWidth(150);
    Label size = new Label("size:");
    size.setPrefWidth(83);
    VBox sizeBox = new VBox();
    sizeBox.setAlignment(Pos.CENTER_LEFT);
    sizeBox.getChildren().addAll(size, this.sizeField);
    sizeBox.setPrefWidth(150);
    Label cost = new Label("cost: ");
    cost.setPrefWidth(84);
    VBox costBox = new VBox();
    costBox.setAlignment(Pos.CENTER_LEFT);
    costBox.getChildren().addAll(cost, this.costField);
    costBox.setPrefWidth(150);
    textBox.getChildren().addAll(idBox, sizeBox, costBox);
    textBox.setSpacing(16);
    HBox buttonBox = new HBox();
    buttonBox.setAlignment(Pos.CENTER_RIGHT);
    buttonBox.getChildren().addAll(updateButton, insertButton, refresh);
    buttonBox.setSpacing(10);
    vBox.getChildren().addAll(message, textBox, buttonBox);
    this.setContent(vBox);
  }

  private void setControls() {
    this.idField = new TextField();
    this.idField.setOnMousePressed(e ->
            this.insertButton.setDisable(true));
    this.idField.setPrefWidth(150);
    this.sizeField = new TextField();
    this.sizeField.setPrefWidth(150);
    this.costField = new TextField();
    this.costField.setPrefWidth(150);
    this.updateButton = new Button("Update");
    this.updateButton.setPrefWidth(80);
    this.updateButton.setOnMousePressed(e -> update());
    this.insertButton = new Button("Insert");
    this.insertButton.setPrefWidth(80);
    this.insertButton.setOnMousePressed(e -> insert());
    this.refresh = new Button("Refresh");
    this.refresh.setPrefWidth(80);
    this.refresh.setOnMousePressed(e -> refresh());
    this.message = new Label();
  }

  private void refresh() {
    updateChipboardTable();
    this.idField.setText("");
    this.insertButton.setDisable(false);
    this.updateButton.setDisable(true);
  }

  private void update() {
    try {
      int id = Integer.parseInt(idField.getText());
      int sizeId = Integer.parseInt(sizeField.getText());
      double cost = Double.parseDouble(costField.getText());
      daoChipboard.update(DaoChipboardInterface.updateById,
              sizeId, cost, id);
      this.message.setText("");
      updateChipboardTable();
    } catch (NumberFormatException e) {
      setMessage("Incorrect data to update");
    }
  }

  private void insert() {
    try {
      int sizeId = Integer.parseInt(sizeField.getText());
      double cost = Double.parseDouble(costField.getText());
      Chipboard chipboard = new Chipboard();
      chipboard.setCost(cost);
      chipboard.setSizeId(sizeId);
      daoChipboard.insert(chipboard);
      this.message.setText("");
      updateChipboardTable();
    } catch (NumberFormatException e) {
      setMessage("Incorrect data to insert");
    }
  }

  private void setMessage(String arg) {
    this.message.setText(arg);
  }

  private void setChipboardTable() {
    this.chipboardTable = new TableView();
    this.chipboardTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn idCol = new TableColumn("Id");
    TableColumn sizeCol = new TableColumn("Size");
    TableColumn costCol = new TableColumn("Price");

    idCol.setCellValueFactory(new PropertyValueFactory<Chipboard, Integer>("id"));
    sizeCol.setCellValueFactory(new PropertyValueFactory<Chipboard, Double>("sizeId"));
    costCol.setCellValueFactory(new PropertyValueFactory<Chipboard, Integer>("cost"));

    updateChipboardTable();

    this.chipboardTable.getSelectionModel().selectedItemProperty()
            .addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        this.idField.setText(((Chipboard)newSelection).getId()+"");
        this.sizeField.setText(((Chipboard)newSelection).getSizeId()+"");
        this.costField.setText(((Chipboard)newSelection).getCost()+"");
        this.insertButton.setDisable(true);
        this.updateButton.setDisable(false);
      }
    });

    this.chipboardTable.getColumns().setAll(idCol, sizeCol, costCol);
  }

  private void updateChipboardTable() {
    ObservableList<Chipboard> ChipboardList = FXCollections.observableArrayList();
    for (Object o : daoChipboard.select()) {
      Chipboard obj = (Chipboard) o;
      ChipboardList.add(obj);
    }
    this.chipboardTable.setItems(ChipboardList);
  }
}
