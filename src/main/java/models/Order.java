package models;

import Database.DaoInterface.IDaoCustomer;
import Database.DaoInterface.IDaoOrderItem;
import Database.DataAccessObject.DaoCustomers;
import Database.DataAccessObject.DaoOrderItem;
import models.enums.OrderStatus;
import models.enums.PaymentStatus;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
  private int id;
  private int customerId;
  private OrderStatus orderStatus;
  private PaymentStatus paymentStatus;
  private double price;

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

  //TODO:
  /*
  - CEO nie ma uprawnien do przyjmowania zaplaty za zamowienie;
  - CEO nie ma uprawnien do insertowania w orderItem;
   */
  public double getPrice() {
    IDaoOrderItem daoOrderItem = DaoOrderItem.getInstance();
    IDaoCustomer daoCustomer = DaoCustomers.getInstance();
    List<OrderItem> itemList = daoOrderItem
            .select(getId());
    final Customer customer = ((Customer) daoCustomer
            .select(getCustomerId())
            .get(0));
    double result = 0;
    try {
      for (OrderItem item : itemList) {
        ((OrderItem) item).setOwner(customer);
        result += item.getPrice();
      }
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return result;
  }
}
