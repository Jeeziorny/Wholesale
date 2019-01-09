package Database.DaoInterface;

import java.sql.Timestamp;
import java.util.List;

public interface DaoIncomeInterface {
  List select(String q, int orderId);
  List select(String q, double value);
  List select(String q, Timestamp date);
}
