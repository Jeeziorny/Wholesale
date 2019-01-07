package models;

import java.sql.Timestamp;

public class Income {
  private int operationId;
  private double operation_value;
  private int orderId;
  private Timestamp date;

  public Income(int operationId, double operation_value, int orderId, Timestamp timestamp) {
    this.operationId = operationId;
    this.operation_value = operation_value;
    this.orderId = orderId;
    this.date = timestamp;
  }

  public Income() {
  }

  public int getOperationId() {
    return operationId;
  }

  public void setOperationId(int operationId) {
    this.operationId = operationId;
  }

  public double getOperation_value() {
    return operation_value;
  }

  public void setOperation_value(double operation_value) {
    this.operation_value = operation_value;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp timestamp) {
    this.date = timestamp;
  }
}
