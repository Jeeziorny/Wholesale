package Database.DaoInterface;

import java.util.List;

public interface DaoChipboardSizeInterface {
  public final String selectById = "FROM ChipboardSize " +
          "WHERE id = :id";

  public final String updateById = "UPDATE chipboard_size " +
          "SET length = :l, width = :w, thicknes =: t " +
          "WHERE id = :id";


  void insert(Object object);
  List select(String q, int id);
  List select();
  int update(int id, int w, int l, int t);
}
