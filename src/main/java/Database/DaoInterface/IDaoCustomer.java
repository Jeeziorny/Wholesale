package Database.DaoInterface;

import java.util.List;

public interface IDaoCustomer {
  /**
   * Insert new Customer
   * @param object - Customer
   */
  void insert(Object object);

  /**
   * Update Customer name by id
   * @param name - new name
   * @param id - id
   * @return - 1 if completed, 0 otherwise
   */
  int update(String name, int id);

  /**
   * Update Customer nip by id
   * @param nip - new nip
   * @param id - id
   * @return - 1 if completed, 0 othewise
   */
  int update(int nip, int id);

  /**
   * Selects Customer by id
   * @param id - customer Id
   * @return - customer with id
   */
  List select(int id);

  /**
   * Selects customer by name
   * @param name her/his name
   * @return Customer obj
   */
  List select(String name);

  /**
   * .
   * @return all Customer in DB
   */
  List select();
}
