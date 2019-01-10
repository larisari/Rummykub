import java.util.List;

public class CombRules {

  private static int MINIMUM_POINTS_FIRST_MOVE = 30;

  private CombRules() {}

  public static boolean isValidFirst(List<Tile> combination) {
    return isValid(combination)
        && PointsCalculator.getPointsOf(combination) >= MINIMUM_POINTS_FIRST_MOVE;
  }

  public static boolean isValid(List<Tile> combination) {

    if (combination.size() < 3) {
      // TODO !!! has to be handled better !!!
      return false;
    }

    // TODO ?? explicit or ??? -> !!! if both is true something went wrong !!!
    return isGroup(combination) || isStreet(combination);
  }

  static boolean isGroup(List<Tile> combination) {

    int combinationLength = combination.size();

    if (combinationLength <= 4 && haveSameNumber(combination)) {
      for (int i = 1; i < combinationLength; i++) {

        Tile previous = combination.get(i - 1);
        Tile current = combination.get(i);

        if (previous.getColor().equals(TileColor.JOKER) || current.getColor().equals(TileColor.JOKER)) {
          break;
        }

        if (current.getColor().equals(previous.getColor())) {
          return false;
        }
      }
    } else {
      return false;
    }

    return true;
  }

  static boolean isStreet(List<Tile> combination) {

    if (haveSameColor(combination) && !haveSameNumber(combination)) {
      for (int i = 1; i < combination.size(); i++) {

        Tile previous = combination.get(i - 1);
        Tile current = combination.get(i);

        if (previous.getNumber().equals(TileNumber.JOKER) || current.getNumber().equals(TileNumber.JOKER)) {
          break;
        }

        if (!(current.getNumber() == (previous.getNumber().next()))) {
          return false;
        }
      }
    } else {
      return false;
    }

    return true;
  }

  private static boolean haveSameNumber(List<Tile> combination) {
    TileNumber tempNum = combination.get(0).getNumber();

    if (tempNum.equals(TileNumber.JOKER)) {
      for (Tile tile : combination) {
        TileNumber num = tile.getNumber();

        if (!num.equals(TileNumber.JOKER)) {
          tempNum = num;
          break;
        }
      }
    }

    TileNumber number = tempNum;

    return combination
        .stream()
        .allMatch(tile -> tile.getNumber().equals(number) || tile.getNumber().equals(TileNumber.JOKER));
  }

  private static boolean haveSameColor(List<Tile> combination) {
    TileColor tempColor = combination.get(0).getColor();

    if (tempColor.equals(TileColor.JOKER)) {
      for (Tile tile : combination) {
        TileColor col = tile.getColor();

        if (!col.equals(TileColor.JOKER)) {
          tempColor = col;
          break;
        }
      }
    }

    TileColor color = tempColor;

    return combination
        .stream()
        .allMatch(tile -> tile.getColor().equals(color) || tile.getColor().equals(TileColor.JOKER));
  }
}
