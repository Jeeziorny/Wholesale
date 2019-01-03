import org.hibernate.Session;

public class Main {
  public static void main(String[] args)
  {
    System.out.println("Maven + Hibernate + MySQL");
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();
    ChipboardSize size = new ChipboardSize();

    size.setWidth(1000);
    size.setLength(999);
    size.setThicknes(45);

    session.save(size);
    session.getTransaction().commit();
  }
}
