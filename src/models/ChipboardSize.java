
public class ChipboardSize {
  private int id;
  private int length;
  private int width;
  private int thicknes;

  public ChipboardSize(int id, int length, int width, int thicknes) {
    this.id = id;
    this.length = length;
    this.width = width;
    this.thicknes = thicknes;
  }

  public ChipboardSize() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getThicknes() {
    return thicknes;
  }

  public void setThicknes(int thicknes) {
    this.thicknes = thicknes;
  }
}
