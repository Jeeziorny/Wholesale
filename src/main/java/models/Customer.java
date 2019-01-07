package models;

public class Customer {
  private int id;
  private int nip;
  private String name;
  private double discount;

  public Customer(int id, int nip, String name, double discount) {
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getDiscount() {
    return discount;
  }

  public void setDiscount(double discount) {
    this.discount = discount;
  }
}
