package Database.DataAccessObject;

import Database.DaoInterface.DaoOrderInterface;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DaoOrder implements DaoOrderInterface {
  private volatile static DaoOrder instance;
  public final String updateOrderStatusById = "UPDATE Order " +
                                              "SET orderStatus = :status " +
                                              "WHERE id = :id";
  public final String updatePaymentStatusById =  "UPDATE Order " +
                                                 "SET paymentStatus = :status " +
                                                 "WHERE id = :id";
  public final String selectByOrderStatus = "FROM Order O " +
                                            "WHERE orderStatus = :status ";
  public final String selectByPaymentStatus = "FROM Order O " +
                                              "WHERE paymentStatus = :status";
  public final String selectById = "FROM Order O " +
                                   "WHERE id = :id";

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
    int result = -1;
    try {
      result = query.executeUpdate();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    session.getTransaction().commit();
    return result;
  }

  public List select(String q, Enum status, int orderId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    if (q.equals(selectById)) {
      query = session.createQuery(selectById);
      query.setParameter("id", orderId);
    } else if (q.equals(selectByOrderStatus)) {
      query = session.createQuery(selectByOrderStatus);
      query.setParameter("status", status);
    } else if (q.equals(selectByPaymentStatus)) {
      query = session.createQuery(selectByPaymentStatus);
      query.setParameter("status", status);
    }
    try {
      return query.list();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }
}
