package Database.DaoInterface;

import models.enums.OrderStatus;
import models.enums.PaymentStatus;

import java.util.List;

public interface IDaoOrder {
  /**
   * Insert new Order
   * @param object - Order
   */
  void insert(Object object);

  /**
   * Update payment status by id
   * @param status - new status
   * @param id - Order id
   * @return - 1 if completed, 0 otherwise
   */
  int update(PaymentStatus status, int id);

  /**
   * Update Order Status by id
   * @param status - new status
   * @param id - order id
   * @return - 1 if completed, 0 othewise
   */
  int update(OrderStatus status, int id);

  /**
   * Selects by order status
   * @param status - order status
   * @return all orders with specified status
   */
  List select(OrderStatus status);

  /**
   * Selects by customerId
   * @param customerId - id of customer
   * @return - Customers orders
   */
  List select(int customerId);

  /**
   * Selects all orders in DB
   * @return
   */
  List select();
}
