package Gui.Office;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PaymentGui implements Observable {
  private static PaymentGui gui;
  InvalidationListener listener;

  private Stage stage;

  public static PaymentGui getInstance() {
    if (gui == null) {
      gui = new PaymentGui();
    }
    return gui;
  }

  public static void start() {
    gui.launch();
  }

  public void launch() {
    stage = new Stage();
    stage.setScene(new Scene(new HBox(new Label("elo"))));
    stage.show();
    stage.setOnCloseRequest(e -> {
      listener.invalidated(this);
    });
  }

  @Override
  public void addListener(InvalidationListener listener) {
    this.listener = listener;
  }

  @Override
  public void removeListener(InvalidationListener listener) {
    this.listener = null;
  }
}
