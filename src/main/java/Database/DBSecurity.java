package Database;

import org.hibernate.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class DBSecurity {
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
      String c = "cmd /c backup.bat "+name;
      System.out.println(c);
      Process p = Runtime
              .getRuntime()
              .exec(c, null, f);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
