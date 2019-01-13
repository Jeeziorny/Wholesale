package Database;

import java.io.File;
import java.io.IOException;

public class DBSecurity implements ISecurity {
  private static volatile DBSecurity instance;

  private DBSecurity() {

  }

  public static DBSecurity getInstance() {
    if (instance == null) {
      instance = new DBSecurity();
    }
    return instance;
  }

  public void backup(String name) {
    try {
      File f = new File("src\\main\\resources");
      String c = "cmd /c backup.bat " + name;
      Process p = Runtime.getRuntime().exec(c, null, f);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int restore(String name) {
    try {
      File f = new File("src\\main\\resources");
      String c = "cmd /c restore.bat " + name;
      Process p = Runtime.getRuntime().exec(c, null, f);
      return p.waitFor();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return -1;
  }
}
