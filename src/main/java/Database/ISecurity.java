package Database;

public interface ISecurity {
  void backup(String name);
  int restore(String name);
}
