package Database.DataAccessObject;

import Database.DaoInterface.DaoChipboardInterface;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DaoChipboard implements DaoChipboardInterface {
  private volatile static DaoChipboard instance;

  private DaoChipboard() {}

  public static DaoChipboard getInstance() {
    if (instance == null) {
      synchronized (DaoOrder.class) {
        if (instance == null) {
          instance = new DaoChipboard();
        }
      }
    }
    return instance;
  }

  public void insert(Object object) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(object);
    session.getTransaction().commit();
  }

  public List select(String q, int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    if (q.equals(selectById)) {
      query = session.createQuery(selectById);
      query.setParameter("id", id);
    } else if (q.equals(selectBySizeId)) {
      query = session.createQuery(selectBySizeId);
      query.setParameter("sizeId", id);
    }
    try {
      return query.list();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List select() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("FROM Chipboard ");
    return query.list();
  }

  @Override
  public int update(String q, int sizeId, double cost, int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    int result = -1;
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      if (q.equals(updateById)) {
        Query query = session.createQuery(updateById);
        query.setParameter("size", sizeId);
        query.setParameter("id", id);
        query.setParameter("cost", cost);
        result = query.executeUpdate();
      }
      tx.commit();
    } catch (RuntimeException e) {
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
}
