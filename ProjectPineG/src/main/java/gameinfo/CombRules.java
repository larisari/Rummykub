package gameinfo;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GITile;

import java.util.List;
import java.util.logging.Logger;

class CombRules {

  private PointsCalculator calculator;
  private static Logger log = Logger.getLogger(CombRules.class.getName());

  CombRules(PointsCalculator calculator) {
    this.calculator = calculator;
  }

  boolean isValid(List<List<GITile>> combinations, int minimumPoints) {
    log.info("Entered Method isValid(combinations, minimumPoints)");

    boolean allValid = combinations.stream().allMatch(this::isValidInternal);

    log.info("The combinations are all " + allValid + ".");

    if (!allValid) {
      return false;
    }

    int points = 0;

    for (List<GITile> combination : combinations) {
      if (isGroup(combination)) {
        points += calculator.calculatePointsForGroup(combination);
        log.info("The passed combination is a group and is worth " + points + " points.");
      } else if (isStreet(combination)) {
        // At this point I know that the combination has to be a street,
        // because otherwise the guard from before would have returned false.
        points += calculator.calculatePointsForStreet(combination);
        log.info("The passed combination is a street and is worth " + points + " points.");
      } else {
        log.info("The combination is neither a group nor a street.");
        throw new IllegalStateException("The combination " + combination + "is not legal.");
      }
    }

    boolean greaterThanMin = points >= minimumPoints;
    log.info(
        "The points of the combination are greater or equal than "
            + minimumPoints
            + " "
            + greaterThanMin
            + ".");

    return greaterThanMin;
  }

  boolean isValid(List<List<GITile>> combinations) {
    log.info("Entered Method isValid(combinations).");
    boolean allValid = combinations.stream().allMatch(this::isValidInternal);
    log.info("All the combinations are " + allValid + ".");
    return allValid;
  }

  private boolean isValidInternal(List<GITile> combination) {
    if (combination.size() < 3) {
      return false;
    }

    return isGroup(combination) || isStreet(combination);
  }

  private boolean isGroup(List<GITile> combination) {

    int combinationLength = combination.size();

    if (combinationLength <= 4 && haveSameNumber(combination)) {
      for (int i = 1; i < combinationLength; i++) {

        GITile previous = combination.get(i - 1);
        GITile current = combination.get(i);

        if (previous.getColor().equals(GIColor.JOKER) || current.getColor().equals(GIColor.JOKER)) {
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

  private boolean isStreet(List<GITile> combination) {

    if (haveSameColor(combination) && !haveSameNumber(combination)) {
      for (int i = 1; i < combination.size(); i++) {

        GITile previous = combination.get(i - 1);
        GITile current = combination.get(i);

        if (previous.getNumber().equals(GINumber.JOKER)
            || current.getNumber().equals(GINumber.JOKER)) {
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

  private boolean haveSameNumber(List<GITile> combination) {
    GINumber tempNum = combination.get(0).getNumber();

    if (tempNum.equals(GINumber.JOKER)) {
      for (GITile tile : combination) {
        GINumber num = tile.getNumber();

        if (!num.equals(GINumber.JOKER)) {
          tempNum = num;
          break;
        }
      }
    }

    GINumber number = tempNum;

    return combination.stream()
        .allMatch(
            tile -> tile.getNumber().equals(number) || tile.getNumber().equals(GINumber.JOKER));
  }

  private boolean haveSameColor(List<GITile> combination) {
    GIColor tempColor = combination.get(0).getColor();

    if (tempColor.equals(GIColor.JOKER)) {
      for (GITile tile : combination) {
        GIColor col = tile.getColor();

        if (!col.equals(GIColor.JOKER)) {
          tempColor = col;
          break;
        }
      }
    }

    GIColor color = tempColor;

    return combination.stream()
        .allMatch(tile -> tile.getColor().equals(color) || tile.getColor().equals(GIColor.JOKER));
  }
}
