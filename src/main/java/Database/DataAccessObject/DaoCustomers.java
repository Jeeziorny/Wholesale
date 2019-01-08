package Database.DataAccessObject;

import Database.DaoInterface.DaoCustomerInterface;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DaoCustomers implements DaoCustomerInterface {
  private volatile static DaoCustomers instance;

  public final String updateNipById = "UPDATE Customer " +
                                      "SET nip = :nip " +
                                      "WHERE id = :id";

  public final String updateNameById = "UPDATE Customer " +
                                       "SET name = :name " +
                                       "WHERE id = :id";

  //TODO: updatowanie kolumny discount to zadanie bazy danych!

  public final String selectByName = "FROM Customer " +
                                     "WHERE name LIKE :name";

  public final String selectById = "FROM Customer " +
                                   "WHERE id = :id";



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

  public int update(String q, Object object, int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    if (q.equals(updateNipById)) {
      query = session.createQuery(updateNipById);
      query.setParameter("nip", object);
      query.setParameter("id", id);
    } else if (q.equals(updateNameById)) {
      query = session.createQuery(updateNameById);
      query.setParameter("name", object);
      query.setParameter("id", id);
    }
    int result = -1;
    try {
      result = query.executeUpdate();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    session.getTransaction().commit();
    return result;
  }

  public List select(String q, Object object) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    if (q.equals(selectById)) {
      query = session.createQuery(selectById);
      query.setParameter("id", object);
    } else if (q.equals(selectByName)) {
      query = session.createQuery(selectByName);
      query.setParameter("name", object);
    }
    try {
      return query.list();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }
}
