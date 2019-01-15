package gameinfo;

import gameinfo.util.GINumber;
import gameinfo.util.GITile;
import gameinfo.util.GIColor;

import java.util.List;

class CombRules {

  private PointsCalculator calculator;

  CombRules(PointsCalculator calculator) {
    this.calculator = calculator;
  }

  boolean isValid(List<GITile> combination, int minimumPoints) {
    if (isGroup(combination)) {
      return calculator.getPointsForGroup(combination) >= minimumPoints;
    } else if (isStreet(combination)) {
      return calculator.getPointsForStreet(combination) >= minimumPoints;
    } else {
      return false;
    }
  }

  boolean isValid(List<GITile> combination) {

    if (combination.size() < 3) {
      // TODO !!! has to be handled better !!!
      return false;
    }

    // TODO ?? explicit or ??? -> !!! if both is true something went wrong !!!
    return isGroup(combination) || isStreet(combination);
  }

  boolean isGroup(List<GITile> combination) {

    int combinationLength = combination.size();

    if (combinationLength <= 4 && haveSameNumber(combination)) {
      for (int i = 1; i < combinationLength; i++) {

        GITile previous = combination.get(i - 1);
        GITile current = combination.get(i);

        if (previous.getColor().equals(GIColor.JOKER)
                || current.getColor().equals(GIColor.JOKER)) {
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

  boolean isStreet(List<GITile> combination) {

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

    return combination
        .stream()
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

    return combination
        .stream()
            .allMatch(tile -> tile.getColor().equals(color) || tile.getColor().equals(GIColor.JOKER));
  }
}
