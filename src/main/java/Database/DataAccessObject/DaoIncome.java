package Database.DataAccessObject;

import Database.DaoInterface.IDaoIncome;
import Database.HibernateUtil;
import models.Income;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

  @Override
  public void delete(Income income) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    String q= "DELETE FROM Income" +
              " WHERE operationId = :id";
    try {
      Query query = session.createQuery(q);
      query.setParameter("id", income.getOperationId());
      query.executeUpdate();
      transaction.commit();
    } catch (Throwable t) {
      transaction.rollback();
      throw t;
    }
  }
}
