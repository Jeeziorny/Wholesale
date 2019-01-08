package Database.DaoInterface;

import java.util.List;

public interface DaoOrderInterface {
  void insert(Object object);
  int update(String q, Enum status, int id);
  List select(String q, Enum status, int id);
}
