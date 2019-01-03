public class Customer {
  private int id;
  private int nip;
  private int name;
  private double discount;

  public Customer(int id, int nip, int name, double discount) {
    this.id = id;
    this.nip = nip;
    this.name = name;
    this.discount = discount;
  }

  public Customer() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getNip() {
    return nip;
  }

  public void setNip(int nip) {
    this.nip = nip;
  }

  public int getName() {
    return name;
  }

  public void setName(int name) {
    this.name = name;
  }

  public double getDiscount() {
    return discount;
  }

  public void setDiscount(double discount) {
    this.discount = discount;
  }
}
