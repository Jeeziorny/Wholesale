import enums.OrderStatus;
import enums.PaymentStatus;

public class Order {
  private int id;
  private int customerId;
  private double charge;
  private OrderStatus orderStatus;
  private PaymentStatus paymentStatus;

  public Order(int id, int customerId, double charge, OrderStatus orderStatus, PaymentStatus paymentStatus) {
    this.id = id;
    this.customerId = customerId;
    this.charge = charge;
    this.orderStatus = orderStatus;
    this.paymentStatus = paymentStatus;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public double getCharge() {
    return charge;
  }

  public void setCharge(double charge) {
    this.charge = charge;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public Order() {

  }
}
