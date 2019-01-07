import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class Main extends Application {
  public static void main(String[] args)
  {
    System.out.println("Maven + Hibernate + MySQL");
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    String q = "SELECT COUNT(*)" +
               "FROM Chipboard";
    Query query = session.createQuery(q);
    List result = query.list();

    System.out.println("ODPOWIEDZ: " + result.get(0) + " <--");

    session.getTransaction().commit();

    launch(args);

  }

  public void start(Stage primaryStage) throws Exception {

  }
}
