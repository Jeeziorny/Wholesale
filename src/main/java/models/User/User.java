package models.User;

import Gui.Ceo.CeoGui;
import Gui.Office.OfficeGui;
import Gui.Warehouse.WarehouseGui;

public class User implements UserInterface {
  private String password;
  private String username;

  public User(String pwd, String username) {
    this.password = pwd;
    this.username = username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getDriverClass() {
    return "com.mysql.jdbc.Driver";
  }

  @Override
  public String getConnectionUrl() {
    return "jdbc:mysql://localhost/wholesale";
  }

  @Override
  public String getDialect() {
    return "org.hibernate.dialect.MySQLDialect";
  }

  @Override
  public void launchGui() {
    if (this.username.equals("warehouseman")) {
      WarehouseGui.getInstance().launch();
    } else if (this.username.equals("office")) {
      OfficeGui.getInstance().launch();
    } else if (this.username.equals("ceo")) {
      CeoGui.getInstance().launch();
    }
  }
}
