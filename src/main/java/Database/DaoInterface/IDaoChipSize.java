package Database.DaoInterface;

import java.util.List;

public interface IDaoChipSize {
  /**
   * Insert new size;
   * @param object
   */
  void insert(Object object);

  /**
   * select size by id
   * @param id - id of some size
   * @return ChipboardSize object
   */
  List select(int id);

  /**
   * Selects all chipboard sizes in DB
   * @return all sizes in DB
   */
  List select();
}
