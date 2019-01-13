package Database;

public interface SecurityInterface {
  void backup(String name);
  int restore(String name);
}
