package Database.DataAccessObject;

import Database.DaoInterface.IDaoOrderItem;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DaoOrderItem implements IDaoOrderItem {
  private volatile static DaoOrderItem instance;

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

  @Override
  public void insert(Object object) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(object);
    session.getTransaction().commit();
  }

  @Override
  public List select(int orderId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    String selectByOrderId = "FROM OrderItem O " +
                             "WHERE orderId = :orderId";
    Query query = session.createQuery(selectByOrderId);
    query.setParameter("orderId", orderId);
    return query.list();
  }
}
