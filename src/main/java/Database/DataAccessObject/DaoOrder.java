package Database.DataAccessObject;

import Database.DaoInterface.IDaoOrder;
import Database.HibernateUtil;
import models.enums.OrderStatus;
import models.enums.PaymentStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DaoOrder implements IDaoOrder {
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

  @Override
  public void insert(Object object) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(object);
    session.getTransaction().commit();
  }

  @Override
  public int update(PaymentStatus status, int orderId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    String updatePaymentStatusById =  "UPDATE Order " +
                                      "SET paymentStatus = :status " +
                                      "WHERE id = :id";
    int result = -1;
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      Query query = session.createQuery(updatePaymentStatusById);
      query.setParameter("status", status);
      query.setParameter("id", orderId);
      result = query.executeUpdate();
      tx.commit();
      update(OrderStatus.PENDING, orderId);
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
  public int update(OrderStatus status, int orderId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    int result = -1;
    Transaction tx = null;
    String updateOrderStatusById = "UPDATE Order " +
                                   "SET orderStatus = :status " +
                                   "WHERE id = :id";
    try {
      tx = session.beginTransaction();
      Query query = session.createQuery(updateOrderStatusById);
      query.setParameter("status", status);
      query.setParameter("id", orderId);
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
  public List select(OrderStatus status) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    String selectByOrderStatus = "FROM Order O " +
                                 "WHERE orderStatus = :status ";
    Query query = session.createQuery(selectByOrderStatus);
    query.setParameter("status", status);
    return query.list();
  }

  @Override
  public List select(int customerId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("FROM Order O WHERE customerId = :id");
    query.setParameter("id", customerId);
    return query.list();
  }

  @Override
  public List select() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("FROM Order ");
    return query.list();
  }
}
