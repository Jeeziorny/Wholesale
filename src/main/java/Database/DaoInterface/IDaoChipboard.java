package Database.DaoInterface;

import java.util.List;

public interface IDaoChipboard {
  /**
   * Insert new Chipboard to DB
   */
  void insert(Object object);

  /**
   * Select by Id
   * @param id id of chipboard we search
   * @return matching object
   */
  List select(int id);

  /**
   * Selects all
   * @return all chipboards in DB
   */
  List select();

  /**
   * Update by Id
   * @param sizeId - new size
   * @param cost - new cost
   * @param id - id of chipboard
   * @return 1 if completed, 0 otherwise
   */
  int update(int sizeId, double cost, int id);
}
