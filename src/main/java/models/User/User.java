package models.User;

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
}
