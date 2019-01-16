package gameinfo.util;

public class GITile {
  private GINumber number;
  private GIColor color;

  /**
   * @throws IllegalArgumentException if Joker is created but either number or color is not of type
   *                                  JOKER.
   */
  public GITile(GINumber number, GIColor color) throws IllegalArgumentException {
    this.number = number;
    this.color = color;
    checkForJoker();
  }

  private void checkForJoker() throws IllegalArgumentException {
    if (number.equals(GINumber.JOKER) && !color.equals(GIColor.JOKER)) {
      throw new IllegalArgumentException("Joker must have GIColor JOKER.");
    } else if (!number.equals(GINumber.JOKER) && color.equals(GIColor.JOKER)) {
      throw new IllegalArgumentException("Joker must have GINumber JOKER.");
    }
  }

  public GINumber getNumber() {
    return this.number;
  }

  public GIColor getColor() {
    return this.color;
  }
}
