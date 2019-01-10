public class Tile {
  private final int MIN_NUM = 1;
  private final int MAX_NUM = 13;
  private final int JOKER = 0;
  private TileNumber number;
  private TileColor color;

  /**
   * @throws IllegalArgumentException if:
   * number not between 0 and 13
   * joker declaration fails
   * */
  public Tile (TileNumber number, TileColor color) {
    if (number.value() >= MIN_NUM && number.value() <= MAX_NUM || number.value() == JOKER) {
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
    if (this.number == TileNumber.JOKER) {
      if (!this.color.equals(TileColor.JOKER)) {
        throw new IllegalArgumentException("Joker must have color JOKER.");
      }
    }
    if (this.color.equals(TileColor.JOKER)) {
      if (this.number != TileNumber.JOKER) {
        throw new IllegalArgumentException("Joker must have number JOKER.");
      }
    }
  }

  @Override public String toString() {
    return "{" + this.number + "," + this.color + "}";
  }

  public TileNumber getNumber() {
    return this.number;
  }

  public TileColor getColor() {
    return this.color;
  }

}