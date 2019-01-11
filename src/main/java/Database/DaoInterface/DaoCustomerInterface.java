package Database.DaoInterface;

import java.util.List;

public interface DaoCustomerInterface {
   String updateNipById = "UPDATE Customer " +
          "SET nip = :nip " +
          "WHERE id = :id";

  String updateNameById = "UPDATE Customer " +
          "SET name = :name " +
          "WHERE id = :id";


  String selectByName = "FROM Customer " +
          "WHERE name LIKE :name";

  String selectById = "FROM Customer " +
          "WHERE id = :id";



  void insert(Object object);
  int update(String q, String name, int id);
  int update(String q, int nip, int id);
  List select(String q, int id);
  List select(String q, String name);
  List select();
}
