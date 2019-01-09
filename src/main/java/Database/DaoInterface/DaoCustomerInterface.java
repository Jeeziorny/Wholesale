package Database.DaoInterface;

import java.util.List;

public interface DaoCustomerInterface {
  void insert(Object object);
  int update(String q, String name, int id);
  int update(String q, int nip, int id);
  List select(String q, int id);
  List select(String q, String name);
}
