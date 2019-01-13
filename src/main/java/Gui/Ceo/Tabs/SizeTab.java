package Gui.Ceo.Tabs;

import Database.DaoInterface.IDaoChipSize;
import Database.DataAccessObject.DaoChipboardSize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.ChipboardSize;

public class SizeTab extends Tab {
  private TableView sizeTable;
  private IDaoChipSize daoSize = DaoChipboardSize.getInstance();

  private TextField length;
  private TextField width;
  private TextField thicknes;

  private Button insertButton;
  private Button refresh;

  private Label message;

  public SizeTab() {
    super("Sizes");
    setSizeTable();
    setControls();
    setLayout();
    this.setClosable(false);
  }

  private void setLayout() {
    VBox vBox = new VBox(this.sizeTable);
    vBox.setSpacing(20);
    vBox.setPadding(new Insets(20));

    HBox textBox = new HBox();

    Label id = new Label("");
    id.setPrefWidth(120);
    VBox idBox = new VBox();
    idBox.setAlignment(Pos.CENTER_LEFT);
    idBox.setPrefWidth(120);

    Label length = new Label("length:");
    length.setPrefWidth(50);
    VBox lengthBox = new VBox();
    lengthBox.setAlignment(Pos.CENTER_LEFT);
    this.length.setPrefWidth(120);
    lengthBox.getChildren().addAll(length, this.length);
    lengthBox.setPrefWidth(120);

    Label width = new Label("width: ");
    width.setPrefWidth(50);
    VBox widthBox = new VBox();
    widthBox.setAlignment(Pos.CENTER_LEFT);
    this.length.setPrefWidth(120);
    widthBox.getChildren().addAll(width, this.width);
    widthBox.setPrefWidth(120);

    Label thicknes = new Label("thicknes: ");
    thicknes.setPrefWidth(50);
    VBox thicknesBox = new VBox();
    thicknesBox.setAlignment(Pos.CENTER_LEFT);
    thicknesBox.getChildren().addAll(thicknes, this.thicknes);
    thicknesBox.setPrefWidth(120);

    textBox.getChildren().addAll(idBox, lengthBox, widthBox, thicknesBox);
    textBox.setSpacing(10);
    HBox buttonBox = new HBox();
    buttonBox.setAlignment(Pos.CENTER_RIGHT);
    buttonBox.getChildren().addAll(insertButton, refresh);
    buttonBox.setSpacing(10);
    vBox.getChildren().addAll(message, textBox, buttonBox);
    this.setContent(vBox);
  }

  private void setControls() {
    this.length = new TextField();
    this.length.setPrefWidth(80);
    this.width = new TextField();
    this.width.setPrefWidth(80);
    this.thicknes = new TextField();
    this.thicknes.setPrefWidth(80);
    this.insertButton = new Button("Insert");
    this.insertButton.setPrefWidth(80);
    this.insertButton.setOnMousePressed(e -> insert());
    this.refresh = new Button("Refresh");
    this.refresh.setPrefWidth(80);
    this.refresh.setOnMousePressed(e -> refresh());
    this.message = new Label();
  }

  private void refresh() {
    updateChipboardSizeTable();
  }

  private void insert() {
    try {
      int length = Integer.parseInt(this.length.getText());
      int width = Integer.parseInt(this.width.getText());
      int thickness = Integer.parseInt(this.thicknes.getText());
      ChipboardSize size = new ChipboardSize();
      size.setLength(length);
      size.setWidth(width);
      size.setThicknes(thickness);
      daoSize.insert(size);
      this.message.setText("");
      updateChipboardSizeTable();
    } catch (NumberFormatException e) {
      setMessage("Incorrect data to insert");
    }
  }

  private void setMessage(String arg) {
    this.message.setText(arg);
  }

  private void setSizeTable() {
    this.sizeTable = new TableView();
    this.sizeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn idCol = new TableColumn("Id");
    TableColumn lengthCol = new TableColumn("Length");
    TableColumn widthCol = new TableColumn("Width");
    TableColumn thicknesCol = new TableColumn("Thicknes");

    idCol.setCellValueFactory(new PropertyValueFactory<ChipboardSize, Integer>("id"));
    lengthCol.setCellValueFactory(new PropertyValueFactory<ChipboardSize, Integer>("length"));
    widthCol.setCellValueFactory(new PropertyValueFactory<ChipboardSize, Integer>("width"));
    thicknesCol.setCellValueFactory(new PropertyValueFactory<ChipboardSize, Integer>("thicknes"));

    updateChipboardSizeTable();

    this.sizeTable.getSelectionModel().selectedItemProperty()
      .addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
          this.length.setText(((ChipboardSize)newSelection).getLength()+"");
          this.width.setText(((ChipboardSize)newSelection).getWidth()+"");
          this.thicknes.setText(((ChipboardSize)newSelection).getThicknes()+"");
        }
      });

    this.sizeTable.getColumns().setAll(idCol, lengthCol, widthCol, thicknesCol);
  }

  private void updateChipboardSizeTable() {
    ObservableList<ChipboardSize> ChipboardSizeList = FXCollections.observableArrayList();
    for (Object o : daoSize.select()) {
      ChipboardSize obj = (ChipboardSize) o;
      ChipboardSizeList.add(obj);
    }
    this.sizeTable.setItems(ChipboardSizeList);
  }
}

//TODO:
/*
Popraw te zdania w interfejsach, niech funkcje dzialaja bez nich.
zrob restore
w office -> orders pokazuj kolumne orderStatus
komentarze
gitara siema
 */
