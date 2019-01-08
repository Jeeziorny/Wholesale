package Database.DataAccessObject;

import Database.DaoInterface.DaoChipboardsizeInterface;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DaoChipboardsize implements DaoChipboardsizeInterface {
  private volatile static DaoChipboardsize instance;

  public final String selectById = "FROM ChipboardSize " +
                                   "WHERE id = :id";

  private DaoChipboardsize() {}

  public static DaoChipboardsize getInstance() {
    if (instance == null) {
      synchronized (DaoOrder.class) {
        if (instance == null) {
          instance = new DaoChipboardsize();
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
    }
    try {
      return query.list();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }
}
