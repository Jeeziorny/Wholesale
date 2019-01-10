package models.enums;

public enum UserEnum {
  WAREHOUSEMAN("UserInterface"),
  OFFICE("Office"),
  CEO("Ceo");

  private final String text;

  UserEnum(final String name) {
    this.text = name;
  }

  String getText() {
    return text;
  }
}
