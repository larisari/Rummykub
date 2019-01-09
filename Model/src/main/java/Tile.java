

public class Tile {
  private final int MIN_NUM = 1;
  private final int MAX_NUM = 13;
  private final int JOKER = 0;
  private int number;
  private Colors color;

  /**
   * @throws IllegalArgumentException if:
   * number not between 0 and 13
   * joker declaration fails
   * */
  public Tile (int number, Colors color) {
    if (number >= MIN_NUM && number <= MAX_NUM || number == JOKER) {
      this.number = number;
      this.color = color;
      checkForJoker();
    }
    else {
      throw new IllegalArgumentException("Invalid argument for Tile " +
          "declaration.");
    }
  }

  private void checkForJoker() {
    if (this.number == JOKER) {
      if (!this.color.equals(Colors.JOKER)) {
        throw new IllegalArgumentException("Joker must have color JOKER.");
      }
    }
    if (this.color.equals(Colors.JOKER)) {
      if (this.number != JOKER) {
        throw new IllegalArgumentException("Joker must have number JOKER.");
      }
    }
  }

  public enum Colors {
    //important to keep JOKER as the last color of the list fot iterating
    RED, BLACK, BLUE, YELLOW, JOKER
  }

  @Override public String toString() {
    return "{" + this.number + "," + this.color + "}";
  }

  public int getNumber() {
    return this.number;
  }

  public Colors getColor() {
    return this.color;
  }

}