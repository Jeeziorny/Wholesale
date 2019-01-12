package Database.DataAccessObject;

import Database.DaoInterface.DaoCustomerInterface;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.util.List;

public class DaoCustomers implements DaoCustomerInterface {
  private volatile static DaoCustomers instance;
  private DaoCustomers() {}

  public static DaoCustomers getInstance() {
    if (instance == null) {
      synchronized (DaoOrder.class) {
        if (instance == null) {
          instance = new DaoCustomers();
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

  public int update(String q, String name, int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    int result = -1;
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      if (q.equals(updateNameById)) {
        Query query = session.createQuery(updateNameById);
        query.setParameter("name", name);
        query.setParameter("id", id);
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

  public int update(String q, int nip, int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    int result = -1;
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      if (q.equals(updateNipById)) {
        Query query = session.createQuery(updateNipById);
        query.setParameter("nip", nip);
        query.setParameter("id", id);
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

  public List select(String q, int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    if (q.equals(selectById)) {
      query = session.createQuery(selectById);
      query.setParameter("id", id);
      return query.list();
    }
    return null;
  }

  public List select(String q, String name) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    if (q.equals(selectByName)) {
      query = session.createQuery(selectByName);
      query.setParameter("name", name);
      return query.list();
    }
    return null;
  }

  public List select() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    query = session.createQuery("FROM Customer ");
    return query.list();
  }
}
