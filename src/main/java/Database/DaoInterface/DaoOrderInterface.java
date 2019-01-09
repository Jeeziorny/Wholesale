package Database.DaoInterface;

import java.util.List;

public interface DaoOrderInterface {
  String updateOrderStatusById = "UPDATE Order " +
          "SET orderStatus = :status " +
          "WHERE id = :id";
  String updatePaymentStatusById =  "UPDATE Order " +
          "SET paymentStatus = :status " +
          "WHERE id = :id";
  String selectByOrderStatus = "FROM Order O " +
          "WHERE orderStatus = :status ";
  String selectByPaymentStatus = "FROM Order O " +
          "WHERE paymentStatus = :status";

  /*TODO:
    Przy updatowaniu statusu platnosci na
    'DONE' wstaw rekord do Income - triggery nie dzialaja;
   */

  /*TODO:
    Sprawdzaj, czy statusy sa dobrze zmieniane;
   */

  void insert(Object object);
  int update(String q, Enum status, int id);
  List select(String q, Enum status);
  List select(int orderId);
  List select();
}
