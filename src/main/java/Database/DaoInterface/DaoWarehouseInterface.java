package Database.DaoInterface;

import java.util.List;

public interface DaoWarehouseInterface {
  int update(String q, int quantity, int id);
  void insert(Object object);
  List select(int id);
  List select();
}
