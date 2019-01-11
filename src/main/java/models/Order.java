package models;

import Database.DaoInterface.DaoChipboardInterface;
import Database.DaoInterface.DaoCustomerInterface;
import Database.DaoInterface.DaoOrderItemIntreface;
import Database.DataAccessObject.DaoChipboard;
import Database.DataAccessObject.DaoCustomers;
import Database.DataAccessObject.DaoOrder;
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

  public double getPrice() {
    DaoOrderItemIntreface daoOrderItem = DaoOrderItem.getInstance();
    DaoCustomerInterface daoCustomer = DaoCustomers.getInstance();
    List<OrderItem> itemList = daoOrderItem
            .select(daoOrderItem.selectByOrderId, getId());
    final Customer customer = ((Customer) daoCustomer
            .select(((DaoCustomers) daoCustomer).selectById, getCustomerId())
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
