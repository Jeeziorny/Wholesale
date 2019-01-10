package Database.DataAccessObject;

import Database.DaoInterface.DaoIncomeInterface;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.util.List;

public class DaoIncome implements DaoIncomeInterface {
  private volatile static DaoIncome instance;

  public final String selectByOrderId = "FROM Income " +
                                        "WHERE orderId = :id";

  public final String selectByOperationValue = "FROM Income " +
                                               "WHERE operation_value = :value";

  public final String selectByDate = "FROM Income " +
          "WHERE operation_date = :date";

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

  public List select(String q, int orderId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    if (q.equals(selectByOrderId)) {
      query = session.createQuery(q);
      query.setParameter("id", orderId);
      return query.list();
    }
    return null;
  }

  public List select(String q, double value) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    if (q.equals(selectByOperationValue)) {
      query = session.createQuery(q);
      query.setParameter("value", value);
      return query.list();
    }
    return null;
  }

  public List select(String q, Timestamp date) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    if (q.equals(selectByDate)) {
      query = session.createQuery(q);
      query.setTimestamp("operation_date", date);
      return query.list();
    }
    return null;
  }
}
