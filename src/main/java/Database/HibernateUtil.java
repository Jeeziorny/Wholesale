package Database;

import Gui.Warehouse.WarehouseGui;
import WholesaleException.IncorrectUserDataException;
import models.User.UserInterface;
import models.enums.UserEnum;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
  private static SessionFactory sessionFactory;

  public static void build(UserInterface user) throws IncorrectUserDataException {
    sessionFactory = buildSessionFactory(user);
    WarehouseGui.getInstance().launch();
  }

  private static SessionFactory buildSessionFactory(UserInterface user) throws IncorrectUserDataException {
    try {
      Configuration cfg = new Configuration();
      cfg.configure();
      System.setProperty("hibernate.connection.password", user.getPassword());
      System.setProperty("hibernate.connection.username", user.getUsername());
      System.setProperty("hibernate.connection.driver_class", user.getDriverClass());
      System.setProperty("hibernate.connection.url", user.getConnectionUrl());
      System.setProperty("hibernate.dialect", user.getDialect());
      cfg.setProperties(System.getProperties());
      return cfg.buildSessionFactory();
    } catch (HibernateException e) {
      throw new IncorrectUserDataException();
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static void shutdown() {
    getSessionFactory().close();
  }
}