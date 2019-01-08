package Database.DaoInterface;

import java.util.List;

public interface DaoOrderItemIntreface {
  void insert(Object object);
  public List select(String q, int orderId);
}
