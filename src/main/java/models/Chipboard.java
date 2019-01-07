package models;

public class Chipboard {
  private int id;
  private int sizeId;
  private double cost;

  public Chipboard(int id, int sizeId, int cost) {
    this.id = id;
    this.sizeId = sizeId;
    this.cost = cost;
  }

  public Chipboard() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSizeId() {
    return sizeId;
  }

  public void setSizeId(int sizeId) {
    this.sizeId = sizeId;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }
}
