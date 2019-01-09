package Database.DaoInterface;

import java.util.List;

public interface DaoOrderItemIntreface {
  String selectByOrderId = "FROM OrderItem O " +
                                        "WHERE orderId = :orderId";

  void insert(Object object);
  public List select(String q, int orderId);
}
