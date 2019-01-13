package Database.DaoInterface;

import java.util.List;

public interface IDaoOrderItem {
  /**
   * insert new order item to DB
   * @param object OrderItem
   */
  void insert(Object object);

  /**
   * selects all orderItem by orderId
   * @param orderId id of order
   * @return all items from specified order
   */
  List select(int orderId);
}
