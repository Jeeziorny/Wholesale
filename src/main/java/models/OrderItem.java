package models;

import Database.DaoInterface.IDaoChipboard;
import Database.DataAccessObject.DaoChipboard;

import java.io.Serializable;
import java.util.List;

public class OrderItem implements Serializable {
  private int chipboardId;
  private int quantity;
  private int orderId;
  private int price;
  private Customer owner;

  public OrderItem(int chipboardId, int quantity, int orderId) {
    this.chipboardId = chipboardId;
    this.quantity = quantity;
    this.orderId = orderId;
  }

  public OrderItem() {
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

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public double getPrice() {
    IDaoChipboard daoChipboard = DaoChipboard.getInstance();
    List<Chipboard> result = ((DaoChipboard) daoChipboard)
            .select(getChipboardId());
    return ((Chipboard) result.get(0)).getCost()
            *this.quantity
            *(1-this.owner.getDiscount());
  }

  public void setOwner(Customer customer) {
    this.owner = customer;
  }
}
