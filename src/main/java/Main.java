import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.Session;

public class Main extends Application {
  public static void main(String[] args)
  {
    System.out.println("Maven + Hibernate + MySQL");
    launch(args);
    Session session = HibernateUtil.getSessionFactory().openSession();
//    session.beginTransaction();
//    ChipboardSize size = new ChipboardSize();
//
//    size.setWidth(1000);
//    size.setLength(999);
//    size.setThicknes(45);
//
//    session.save(size);
//    session.getTransaction().commit();

  }

  public void start(Stage primaryStage) throws Exception {

  }
}
