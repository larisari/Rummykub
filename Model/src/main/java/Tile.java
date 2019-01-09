

public class Tile {
  private final int MIN_NUM = 1;
  private final int MAX_NUM = 13;
  private final int JOKER = 0;
  private int number;
  private Color color;

  /**
   * @throws IllegalArgumentException if:
   * number not between 0 and 13
   * joker declaration fails
   * */
  public Tile (int number, Color color) {
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
      if (!this.color.equals(Color.JOKER)) {
        throw new IllegalArgumentException("Joker must have color JOKER.");
      }
    }
    if (this.color.equals(Color.JOKER)) {
      if (this.number != JOKER) {
        throw new IllegalArgumentException("Joker must have number JOKER.");
      }
    }
  }

  @Override public String toString() {
    return "{" + this.number + "," + this.color + "}";
  }

  public int getNumber() {
    return this.number;
  }

  public Color getColor() {
    return this.color;
  }

}