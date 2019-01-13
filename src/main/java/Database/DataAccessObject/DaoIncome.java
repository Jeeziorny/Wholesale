package Database.DataAccessObject;

import Database.DaoInterface.IDaoIncome;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DaoIncome implements IDaoIncome {
  private volatile static DaoIncome instance;

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

  @Override
  public List select() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("FROM Income ");
    return query.list();
  }
}
