public class OrderItem {
  private int chipboardId;
  private int quantity;
  private int orderId;

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
}
