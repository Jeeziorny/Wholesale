package Database.DaoInterface;

import java.util.List;

public interface DaoCustomerInterface {
  void insert(Object object);
  int update(String q, Object object, int id);
  public List select(String q, Object object);
}
