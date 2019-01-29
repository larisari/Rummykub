/**
 * This Class serves as a validator for combinations a player wants to put on the board. It is a
 * pure calculating class that mainly checks whether a move makes sense according to the game's
 * rules or not.
 */
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

  /**
   * Method to check whether given combinations exceed the minimum of a given amount of points.
   *
   * @param combinations to be put on board.
   * @param minimumPoints for a valid move.
   * @return true, if move is valid, and false otherwise.
   */
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
      } else {
        // At this point I know that the combination has to be a street,
        // because otherwise the guard from before would have returned false.
        points += calculator.calculatePointsForStreet(combination);
        log.info("The passed combination is a street and is worth " + points + " points.");
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

  /**
   * Method to check whether one or several combinations are valid according to the game's rules.
   *
   * @param combinations to be set on the board.
   * @return true, if move is possible, and false otherwise.
   */
  boolean isValid(List<List<GITile>> combinations) {
    log.info("Entered Method isValid(combinations).");
    boolean allValid = combinations.stream().allMatch(this::isValidInternal);
    log.info("All the combinations are " + allValid + ".");
    return allValid;
  }

  private boolean isValidInternal(List<GITile> combination) {
    if (combination.size() < 3 || combination.size() > 13) {
      return false;
    }

    counterStart = 0;
    return isGroup(combination) || isStreet(combination);
  }

  private boolean isGroup(List<GITile> combination) {

    int combinationLength = combination.size();

    if (combinationLength <= 4 && haveSameNumber(combination) && !haveSameColor(combination)) {
      for (int i = 1; i < combinationLength; i++) {

        GITile previous = combination.get(i - 1);
        GITile current = combination.get(i);

        if (previous.getColor().equals(GIColor.JOKER) || current.getColor().equals(GIColor.JOKER)) {
          continue;
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

      GINumber reference = calculateReferenceFor(combination);
      return checkForStreet(combination, reference);

    } else {
      return false;
    }
  }

  private static int counterStart = 0;

  private boolean checkForStreet(List<GITile> combination, GINumber reference) {
    log.info("Reference for checkStreet is " + reference);

    for (int i = counterStart; i < combination.size(); i++) {
      GITile currentTile = combination.get(i);

      log.info("Updated reference for iteration " + i + " in checkStreet is " + reference);

      if (currentTile.getNumber().equals(GINumber.JOKER)) {
        reference = reference.next();
        continue;
      }

      if (!currentTile.getNumber().previous().equals(reference)) {
        return false;
      }

      reference = reference.next();
    }

    return true;
  }

  private GINumber calculateReferenceFor(List<GITile> combination) {
    GINumber tempNum = combination.get(0).getNumber();

    if (tempNum.equals(GINumber.JOKER)) {
      for (GITile tile : combination) {
        GINumber num = tile.getNumber();

        if (!num.equals(GINumber.JOKER)) {
          return num.previous();
        }

        counterStart++;
      }
    }

    return tempNum.previous();
  }

  private boolean haveSameNumber(List<GITile> combination) {
    GINumber number = calculateFirstRealNumberOf(combination);

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

  private GINumber calculateFirstRealNumberOf(List<GITile> combination) {
    GINumber tempNum = combination.get(0).getNumber();

    if (tempNum.equals(GINumber.JOKER)) {
      for (GITile tile : combination) {
        GINumber num = tile.getNumber();

        if (!num.equals(GINumber.JOKER)) {
          return num;
        }
      }
    }

    return tempNum;
  }
}
