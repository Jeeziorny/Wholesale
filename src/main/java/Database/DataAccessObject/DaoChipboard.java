package Database.DataAccessObject;

import Database.DaoInterface.IDaoChipboard;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DaoChipboard implements IDaoChipboard {
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

  @Override
  public void insert(Object object) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(object);
    session.getTransaction().commit();
  }

  @Override
  public List select(int id) {
    Session session = HibernateUtil
            .getSessionFactory().openSession();
    String selectById = "FROM Chipboard " +
            "WHERE id = :id";
    Query query = session.createQuery(selectById);
    query.setParameter("id", id);
    return query.list();
  }

  @Override
  public List select() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("FROM Chipboard ");
    return query.list();
  }

  @Override
  public int update(int sizeId, double cost, int id) {
    String updateById = "UPDATE Chipboard " +
                        "SET sizeId = :size, " +
                        "cost = :cost " +
                        "WHERE id = :id ";
    Session session = HibernateUtil.getSessionFactory().openSession();
    int result = -1;
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      Query query = session.createQuery(updateById);
      query.setParameter("size", sizeId);
      query.setParameter("id", id);
      query.setParameter("cost", cost);
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
}
