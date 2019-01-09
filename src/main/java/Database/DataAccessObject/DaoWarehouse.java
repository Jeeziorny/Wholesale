package Database.DataAccessObject;

import Database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class DaoWarehouse {
  public final String updateQuantityById = "UPDATE Warehouse " +
                                       "SET name = :quantity " +
                                       "WHERE id = :id";

  public int update(String q, int quantity, int id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    int result = -1;
    if (q.equals(updateQuantityById)) {
      query = session.createQuery(q);
      query.setParameter("quantity", quantity);
      query.setParameter("id", id);
      result = query.executeUpdate();
    }
    session.getTransaction().commit();
    return result;
  }
}
