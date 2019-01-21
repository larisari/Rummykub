package gameinfo;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GITile;

import java.util.List;

class CombRules {

  private PointsCalculator calculator;

  CombRules(PointsCalculator calculator) {
    this.calculator = calculator;
  }

  boolean isValid(List<List<GITile>> combinations, int minimumPoints) {
    boolean allValid = combinations.stream().allMatch(this::isValidInternal);

    if (!allValid) {
      return false;
    }

    int points = 0;

    for (List<GITile> combination : combinations) {
      if (isGroup(combination)) {
        points += calculator.calculatePointsForGroup(combination);
      } else {
        // At this point I know that the combination has to be a street,
        // because otherwise the guard from before would have returned false.
        points += calculator.calculatePointsForStreet(combination);
      }
    }

    return points >= minimumPoints;
  }

  boolean isValid(List<List<GITile>> combinations) {
    return combinations.stream().allMatch(this::isValidInternal);
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
