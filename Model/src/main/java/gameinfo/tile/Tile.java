package gameinfo.tile;

import gameinfo.tile.util.Color;
import gameinfo.tile.util.Number;

public class Tile {
  private final int MIN_NUM = 1;
  private final int MAX_NUM = 13;
  private final int JOKER = 0;
  private Number number;
  private Color color;

  /**
   * @throws IllegalArgumentException if:
   * number not between 0 and 13
   * joker declaration fails
   * */
  public Tile(Number number, Color color) {
    if (number.value() >= MIN_NUM && number.value() <= MAX_NUM || number.value() == JOKER) {
      this.number = number;
      this.color = color;
      checkForJoker();
    }
    else {
      throw new IllegalArgumentException("Invalid argument for gameinfo.tile.Tile " +
          "declaration.");
    }
  }

  private void checkForJoker() {
    if (this.number == Number.JOKER) {
      if (!this.color.equals(Color.JOKER)) {
        throw new IllegalArgumentException("Joker must have color JOKER.");
      }
    }
    if (this.color.equals(Color.JOKER)) {
      if (this.number != Number.JOKER) {
        throw new IllegalArgumentException("Joker must have number JOKER.");
      }
    }
  }

  @Override public String toString() {
    return "{" + this.number + "," + this.color + "}";
  }

  public Number getNumber() {
    return this.number;
  }

  public Color getColor() {
    return this.color;
  }

}