package Database;

import Gui.Warehouse.WarehouseGui;
import models.Warehouse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
  private static SessionFactory sessionFactory;

  public static void build(String resource) {
    sessionFactory = buildSessionFactory(resource);
    WarehouseGui warehouseGui = new WarehouseGui();
  }

  private static SessionFactory buildSessionFactory(String resource) {
    try {
      return new Configuration().addResource(resource).configure().buildSessionFactory();
    }
    catch (Throwable ex) {
      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static void shutdown() {
    getSessionFactory().close();
  }
}