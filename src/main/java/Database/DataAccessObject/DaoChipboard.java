package Database.DataAccessObject;

import Database.DaoInterface.DaoChipboardsizeInterface;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DaoChipboard implements DaoChipboardsizeInterface {
  private volatile static DaoChipboard instance;

  public final String selectById = "FROM Chipboard " +
                                   "WHERE id = :id";

  public final String selectBySizeId = "FROM Chipboard " +
                                       "WHERE sizeId = :sizeId";

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
}
