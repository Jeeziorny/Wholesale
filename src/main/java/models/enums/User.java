package models.enums;

public enum User {
  WAREHOUSEMAN("Warehouseman"),
  OFFICE("Office"),
  CEO("Ceo");

  private final String text;

  User(final String name) {
    this.text = name;
  }

  String getText() {
    return text;
  }
}
