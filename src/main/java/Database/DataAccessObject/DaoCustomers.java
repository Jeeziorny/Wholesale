package Database.DataAccessObject;

import Database.DaoInterface.IDaoCustomer;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DaoCustomers implements IDaoCustomer {
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

  @Override
  public void insert(Object object) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(object);
    session.getTransaction().commit();
  }

  @Override
  public int update(String name, int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    String updateNameById = "UPDATE Customer " +
                            "SET name = :name " +
                            "WHERE id = :id";
    int result = -1;
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      Query query = session.createQuery(updateNameById);
      query.setParameter("name", name);
      query.setParameter("id", id);
      result = query.executeUpdate();
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

  @Override
  public int update(int nip, int id) {
    String updateNipById = "UPDATE Customer " +
                            "SET nip = :nip " +
                            "WHERE id = :id";
    Session session = HibernateUtil.getSessionFactory().openSession();
    int result = -1;
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      Query query = session.createQuery(updateNipById);
      query.setParameter("nip", nip);
      query.setParameter("id", id);
      result = query.executeUpdate();
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

  @Override
  public List select(int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    String selectById = "FROM Customer " +
                        "WHERE id = :id";
    Query query = session.createQuery(selectById);
    query.setParameter("id", id);
    return query.list();
  }

  @Override
  public List select(String name) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    String selectByName = "FROM Customer " +
                          "WHERE name LIKE :name";
    Query query = session.createQuery(selectByName);
    query.setParameter("name", name);
    return query.list();
  }

  @Override
  public List select() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("FROM Customer ");
    return query.list();
  }
}
