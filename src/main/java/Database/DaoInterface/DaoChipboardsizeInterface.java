package Database.DaoInterface;

import java.util.List;

public interface DaoChipboardsizeInterface {
  void insert(Object object);
  List select(String q, int id);
}
