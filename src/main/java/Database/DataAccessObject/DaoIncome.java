package Database.DataAccessObject;

import java.util.List;

public class DaoIncome {
  private volatile static DaoIncome instance;

  public final String selectByOrderId = "FROM Income " +
                                        "WHERE orderId = :id";

  public final String selectByOperationValue = "FROM Income " +
                                               "WHERE operation_value = :value";

  public final String selectByDate = "FROM Income " +
                                     "WHERE operation_date"

  //TODO: Insertowanie jest zadaniem bazy danych!

  private DaoIncome() {}

  public static DaoIncome getInstance() {
    if (instance == null) {
      synchronized (DaoOrder.class) {
        if (instance == null) {
          instance = new DaoIncome();
        }
      }
    }
    return instance;
  }

  public List select(String q, )

}
