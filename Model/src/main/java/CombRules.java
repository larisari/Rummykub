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

  private static boolean isGroup(List<Tile> combination) {

    int combinationLength = combination.size();

    if (combinationLength <= 4 && haveSameNumber(combination)) {
      for (int i = 1; i < combinationLength; i++) {
        if (combination.get(i).getColor().equals(combination.get(i - 1).getColor())) {
          return false;
        }
      }
    } else {
      return false;
    }

    return true;
  }

  private static boolean isStreet(List<Tile> combination) {

    if (haveSameColor(combination) && !haveSameNumber(combination)) {
      for (int i = 1; i < combination.size(); i++) {
        if (!(combination.get(i).getNumber() == (combination.get(i - 1).getNumber().next()))) {
          return false;
        }
      }
    } else {
      return false;
    }

    return true;
  }

  private static boolean haveSameNumber(List<Tile> combination) {
    Number number = combination.get(1).getNumber();

    return combination.stream().allMatch(tile -> tile.getNumber().equals(number));
  }

  private static boolean haveSameColor(List<Tile> combination) {
    Color color = combination.get(1).getColor();

    return combination.stream().allMatch(tile -> tile.getColor().equals(color));
  }
}
