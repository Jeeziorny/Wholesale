package models;

import models.enums.OrderStatus;
import models.enums.PaymentStatus;

public class Order {
  private int id;
  private int customerId;
  private OrderStatus orderStatus;
  private PaymentStatus paymentStatus;

  public Order(int id, int customerId, double charge, OrderStatus orderStatus, PaymentStatus paymentStatus) {
    this.id = id;
    this.customerId = customerId;
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
