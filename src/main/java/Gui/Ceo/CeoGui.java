package Gui.Ceo;
import Gui.Ceo.Tabs.*;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CeoGui {
  private static CeoGui instance;

  private Stage stage;
  private TabPane tabPane = new TabPane();

  private IncomeTab incomeTab = new IncomeTab();
  private WarehouseTab warehouseTab = new WarehouseTab();
  private ChipboardTab chipboardTab = new ChipboardTab();
  private SecurityTab dataBaseTab = new SecurityTab();
  private SizeTab sizeTab = new SizeTab();

  public static CeoGui getInstance() {
    if (instance == null) {
      instance = new CeoGui();
    }
    return instance;
  }

  public static void launch() {
    instance.getStage().show();
    instance.getStage().setResizable(false);
  }

  private CeoGui() {
    setTabPane();
    this.stage = new Stage();
    this.stage.setTitle("Wholesale: Ceo");
    this.stage.setScene(new Scene(tabPane));
    ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
            System.out.println("Height: " + stage.getHeight() + " Width: " + stage.getWidth());

    stage.widthProperty().addListener(stageSizeListener);
    stage.heightProperty().addListener(stageSizeListener);
  }

  private Stage getStage() {
    return this.stage;
  }

  private void setTabPane() {
    this.tabPane.setPrefWidth(508);
    this.tabPane.setPrefHeight(568);
    this.tabPane.getTabs().addAll(incomeTab, warehouseTab, chipboardTab, dataBaseTab, sizeTab);
  }
}
