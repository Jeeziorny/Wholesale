package models;

import java.io.Serializable;

public class Warehouse implements Serializable {
  private int chipboardId;
  private int quantity;

  public Warehouse (int chipboardId, int quantity) {
    this.chipboardId = chipboardId;
    this.quantity = quantity;
  }

  public Warehouse() {
  }

  public int getChipboardId() {
    return chipboardId;
  }

  public void setChipboardId(int chipboardId) {
    this.chipboardId = chipboardId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
