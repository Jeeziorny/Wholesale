package Database.DataAccessObject;

import Database.DaoInterface.IDaoWarehouse;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DaoWarehouse implements IDaoWarehouse {
  private static volatile DaoWarehouse instance;

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
  public int update(int quantity, int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    String updateQuantityById = "UPDATE Warehouse " +
                                "SET quantity = :q " +
                                "WHERE chipboardId = :id";
    int result = -1;
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      Query query = session.createQuery(updateQuantityById);
      query.setParameter("q", quantity);
      query.setParameter("id", id);
      result = query.executeUpdate();
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
    String selectById = "FROM Warehouse WHERE chipboardId = :id";
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
