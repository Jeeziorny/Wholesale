package Gui.Ceo.Tabs;

import Database.DaoInterface.IDaoIncome;
import Database.DataAccessObject.DaoIncome;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import models.Income;

import java.sql.Timestamp;

public class IncomeTab extends Tab {
  private TableView incomeTable;
  private IDaoIncome daoIncome = DaoIncome.getInstance();

  public IncomeTab() {
    super("Income");
    setIncomeTable();
    setLayout();
    this.setClosable(false);
  }

  private void setLayout() {
    VBox vBox = new VBox(incomeTable);
    incomeTable.setPrefHeight(590);
    vBox.setPadding(new Insets(20));
    this.setContent(vBox);
  }

  private void setIncomeTable() {
    this.incomeTable = new TableView();
    this.incomeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn idCol = new TableColumn("Id");
    TableColumn valueCol = new TableColumn("Value");
    TableColumn orderCol = new TableColumn("Order");
    TableColumn dateCol = new TableColumn("Date");

    idCol.setCellValueFactory(new PropertyValueFactory<Income, Integer>("operationId"));
    valueCol.setCellValueFactory(new PropertyValueFactory<Income, Double>("operation_value"));
    orderCol.setCellValueFactory(new PropertyValueFactory<Income, Integer>("orderId"));
    dateCol.setCellValueFactory(new PropertyValueFactory<Income, Timestamp>("date"));

    updateIncomeTable();

    this.incomeTable.getColumns().setAll(idCol, valueCol, orderCol, dateCol);
  }

  private void updateIncomeTable() {
    ObservableList<Income> incomeList = FXCollections.observableArrayList();
    for (Object o : daoIncome.select()) {
      Income obj = (Income) o;
      incomeList.add(obj);
    }
    this.incomeTable.setItems(incomeList);
  }
}
