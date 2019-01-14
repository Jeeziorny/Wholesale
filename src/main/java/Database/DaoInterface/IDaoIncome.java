package Database.DaoInterface;

import models.Income;

import java.util.List;

public interface IDaoIncome {
  /**
   * .
   * @return all incomes in DB
   */
  List select();

  /**
   * Deletes income from DB
   * @param income - Income object
   */
  void delete(Income income);
}
