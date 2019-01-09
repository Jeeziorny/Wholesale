package Database.DaoInterface;

import java.util.List;

public interface DaoChipboardInterface {
  void insert(Object object);
  List select(String q, int id);
}
