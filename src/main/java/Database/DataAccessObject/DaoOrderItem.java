package Database.DataAccessObject;

import Database.DaoInterface.DaoOrderItemIntreface;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DaoOrderItem implements DaoOrderItemIntreface {
  private volatile static DaoOrderItem instance;

  public final String selectByOrderId = "FROM OrderItem O " +
                                        "WHERE orderId = :orderId";

  private DaoOrderItem() {}

  public static DaoOrderItem getInstance() {
    if (instance == null) {
      synchronized (DaoOrder.class) {
        if (instance == null) {
          instance = new DaoOrderItem();
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

  public List select(String q, int orderId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    if (q.equals(selectByOrderId)) {
      query = session.createQuery(selectByOrderId);
      query.setParameter("orderId", orderId);
    }
    try {
      return query.list();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }
}
