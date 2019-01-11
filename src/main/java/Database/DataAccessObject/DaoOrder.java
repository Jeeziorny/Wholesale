package Database.DataAccessObject;

import Database.DaoInterface.DaoOrderInterface;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.util.List;

public class DaoOrder implements DaoOrderInterface {
  private volatile static DaoOrder instance;

  private DaoOrder() {}

  public static DaoOrder getInstance() {
    if (instance == null) {
      synchronized (DaoOrder.class) {
        if (instance == null) {
          instance = new DaoOrder();
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

  public int update(String q, Enum status, int orderId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    int result = -1;
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      Query query = null;
      if (q.equals(updateOrderStatusById)) {
        query = session.createQuery(updateOrderStatusById);
        query.setParameter("status", status);
        query.setParameter("id", orderId);
      } else if (q.equals(updatePaymentStatusById)) {
        query = session.createQuery(updatePaymentStatusById);
        query.setParameter("status", status);
        query.setParameter("id", orderId);
      }
      try {
        result = query.executeUpdate();
      } catch (NullPointerException e) {
        e.printStackTrace();
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

  public List select(String q, Enum status) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query;
     if (q.equals(selectByOrderStatus)) {
      query = session.createQuery(selectByOrderStatus);
      query.setParameter("status", status);
      return query.list();
    } else if (q.equals(selectByPaymentStatus)) {
      query = session.createQuery(selectByPaymentStatus);
      query.setParameter("status", status);
      return query.list();
    }
    return null;
  }

  public List select(int customerId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("FROM Order O WHERE customerId = :id");
    query.setParameter("id", customerId);
    return query.list();
  }

  public List select() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("FROM Order ");
    return query.list();
  }
}
