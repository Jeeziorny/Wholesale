package Database.DataAccessObject;

import Database.DaoInterface.DaoWarehouseInterface;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DaoWarehouse implements DaoWarehouseInterface {
  private static volatile DaoWarehouse instance;

  public static final String updateQuantityById = "UPDATE Warehouse " +
                                       "SET quantity = :q " +
                                       "WHERE chipboardId = :id";

  public static final String selectById = "FROM Warehouse WHERE chipboardId = :id";

  public static DaoWarehouse getInstance() {
    if (instance == null) {
      synchronized (DaoOrder.class) {
        if (instance == null) {
          instance = new DaoWarehouse();
        }
      }
    }
    return instance;
  }

  @Override
  public int update(String q, int quantity, int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    int result = -1;
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      if (q.equals(updateQuantityById)) {
        Query query = session.createQuery(q);
        query.setParameter("q", quantity);
        query.setParameter("id", id);
        try {
          result = query.executeUpdate();
        } catch (NullPointerException e) {
          e.printStackTrace();
        }
      }
      tx.commit();
    }  catch (RuntimeException e) {
      if (tx != null) {
        tx.rollback();
        e.printStackTrace();
      }
    }
    finally {
      session.close();
    }
    return result;
  }

  @Override
  public void insert(Object object) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(object);
    session.getTransaction().commit();
  }

  @Override
  public List select(int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery(selectById);
    query.setParameter("id", id);
    return query.list();
  }

  @Override
  public List select() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("FROM Warehouse ");
    return query.list();
  }
}
