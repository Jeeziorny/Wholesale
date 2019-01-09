import models.*;
import models.enums.OrderStatus;
import models.enums.PaymentStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WholesaleInsertTest {
  private static SessionFactory sessionFactory;
  private static Session session = null;

  private Chipboard chipboard;
  private ChipboardSize chipboardSize;
  private Customer customer;
  private Income income;
  private Order order;
  private OrderItem orderItem;
  private Warehouse warehouse;

  @BeforeClass
  public static void before() {
    //setup the session factory
    try {
      sessionFactory = new Configuration().addResource("hibernate.cfg.xml")
              .configure().buildSessionFactory();
    }
    catch (Throwable ex) {
      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
    session = sessionFactory.openSession();
    session.beginTransaction();
  }

  @Test
  public void testInsertingData() {
    //due to dependencies in db e.g. foreign keys
    //there should be special order in inserting.
    //inserting to chipboardSize
    String q = "SELECT COUNT(*)" +
               "FROM ChipboardSize ";
    Query query = session.createQuery(q);
    List oldResult = query.list();
    this.chipboardSize = new ChipboardSize();
    chipboardSize.setThicknes(35);
    chipboardSize.setLength(2000);
    chipboardSize.setWidth(3000);
    session.save(chipboardSize);
    List newResult = query.list();
    assertEquals((Long) oldResult.get(0)+1, newResult.get(0));

    //inserting to chipboard;
    q = "SELECT COUNT(*)" +
        "FROM Chipboard";
    query = session.createQuery(q);
    oldResult = query.list();
    this.chipboard = new Chipboard();
    chipboard.setCost(10000);
    chipboard.setSizeId(chipboardSize.getId());
    session.save(chipboard);
    newResult = query.list();
    assertEquals((Long) oldResult.get(0)+1, newResult.get(0));

    //inserting to customer;
    q = "SELECT COUNT(*)" +
        "FROM Customer ";
    query = session.createQuery(q);
    oldResult = query.list();
    this.customer = new Customer();
    customer.setName("TESTNAME");
    customer.setNip(-1000000);
    customer.setDiscount(0);
    session.save(customer);
    newResult = query.list();
    assertEquals((Long) oldResult.get(0) + 1, newResult.get(0));

    //inserting to order;
    q = "SELECT COUNT(*)" +
        "FROM Order ";
    query = session.createQuery(q);
    oldResult = query.list();
    this.order = new Order();
    order.setCustomerId(customer.getId());
    order.setOrderStatus(OrderStatus.DONE);
    order.setPaymentStatus(PaymentStatus.DONE);
    session.save(order);
    newResult = query.list();
    assertEquals((Long)oldResult.get(0) + 1, newResult.get(0));

    //inserting to orderItem;
    q = "SELECT COUNT(*)" +
        "FROM OrderItem ";
    query = session.createQuery(q);
    oldResult = query.list();
    this.orderItem = new OrderItem();
    orderItem.setChipboardId(chipboard.getId());
    orderItem.setOrderId(order.getId());
    orderItem.setQuantity(1);
    session.save(orderItem);
    newResult = query.list();
    assertEquals((Long) oldResult.get(0) + 1, newResult.get(0));

    //inserting to models.Income;
    q = "SELECT COUNT(*)" +
        "FROM Income";
    query = session.createQuery(q);
    oldResult = query.list();
    this.income = new Income();
    income.setDate(new Timestamp(System.currentTimeMillis()));
    income.setOperation_value(chipboard.getCost());
    income.setOrderId(order.getId());
    session.save(income);
    newResult = query.list();
    assertEquals((Long) oldResult.get(0) + 1, newResult.get(0));

    //inserting to warehouse;
    q = "SELECT COUNT(*)" +
        "FROM Warehouse ";
    query = session.createQuery(q);
    oldResult = query.list();
    this.warehouse = new Warehouse();
    warehouse.setChipboardId(chipboard.getId());
    warehouse.setQuantity(1);
    session.save(warehouse);
    newResult = query.list();
    assertEquals((Long) oldResult.get(0) + 1, newResult.get(0));

    //updating
    Query query3 = session.createQuery("UPDATE Order" +
                                      " SET orderStatus = :status" +
                                      " WHERE id = :id");
    query3.setParameter("status", OrderStatus.DONE);
    query3.setParameter("id", 2);
    query3.executeUpdate();

  }

  @After
  public void after() {
    session.delete(income);
    session.delete(warehouse);
    session.delete(orderItem);
    session.delete(order);
    session.delete(customer);
    session.delete(chipboard);
    session.delete(chipboardSize);

    session.getTransaction().commit();
  }
}
