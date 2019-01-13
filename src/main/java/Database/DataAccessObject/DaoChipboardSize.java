package Database.DataAccessObject;

import Database.DaoInterface.IDaoChipSize;
import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DaoChipboardSize implements IDaoChipSize {
  private volatile static DaoChipboardSize instance;

  private DaoChipboardSize() {}

  public static DaoChipboardSize getInstance() {
    if (instance == null) {
      synchronized (DaoOrder.class) {
        if (instance == null) {
          instance = new DaoChipboardSize();
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
    Session session = HibernateUtil.getSessionFactory().openSession();
    String selectById = "FROM ChipboardSize " +
                        "WHERE id = :id";
    Query query = session.createQuery(selectById);
    query.setParameter("id", id);
    try {
      return query.list();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List select() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("FROM ChipboardSize ");
    return query.list();
  }
}
