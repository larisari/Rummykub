package gameinfo.util;

public class GITile {
  private final int MIN_NUM = 1;
  private final int MAX_NUM = 13;
  private final int JOKER = 0;
  private GINumber number;
  private GIColor color;

  /**
   * @throws IllegalArgumentException if:
   * number not between 0 and 13
   * joker declaration fails
   * */
  public GITile(GINumber number, GIColor color) {
    if (number.value() >= MIN_NUM && number.value() <= MAX_NUM || number.value() == JOKER) {
      this.number = number;
      this.color = color;
      checkForJoker();
    }
    else {
      throw new IllegalArgumentException("Invalid argument for gameinfo.util.GITile " +
          "declaration.");
    }
  }

  private void checkForJoker() {
    if (this.number == GINumber.JOKER) {
      if (!this.color.equals(GIColor.JOKER)) {
        throw new IllegalArgumentException("Joker must have color JOKER.");
      }
    }
    if (this.color.equals(GIColor.JOKER)) {
      if (this.number != GINumber.JOKER) {
        throw new IllegalArgumentException("Joker must have number JOKER.");
      }
    }
  }

  public GINumber getNumber() {
    return this.number;
  }

  public GIColor getColor() {
    return this.color;
  }

}