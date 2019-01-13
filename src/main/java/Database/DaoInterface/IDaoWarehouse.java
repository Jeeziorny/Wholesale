package Database.DaoInterface;

import java.util.List;

public interface IDaoWarehouse {
  /**
   * Updates quantity of some chipboard in warehouse
   * @param quantity - new quantity
   * @param id - id of chipboard
   * @return - 1 if completed, 0 otherwise
   */
  int update(int quantity, int id);

  /**
   * insert new Chipboard to warehouse
   * @param object Warehouse
   */
  void insert(Object object);

  /**
   * Selects Warehouse position by id
   * @param id - id of warehouse position
   * @return - Warehouse object
   */
  List select(int id);

  /**
   * Selects all from Warehouse
   * @return list of all chipboard in warehouse
   */
  List select();
}
