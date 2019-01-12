package gameinfo;

import gameinfo.tile.Tile;
import gameinfo.tile.util.Color;
import gameinfo.tile.util.Number;

import java.util.List;

class CombRules {

  private PointsCalculator calculator;

  CombRules(PointsCalculator calculator) {
    this.calculator = calculator;
  }

  boolean isValid(List<Tile> combination, int minimumPoints) {
    if (isGroup(combination)) {
      return calculator.getPointsForGroup(combination) >= minimumPoints;
    } else if (isStreet(combination)) {
      return calculator.getPointsForStreet(combination) >= minimumPoints;
    } else {
      return false;
    }
  }

  boolean isValid(List<Tile> combination) {

    if (combination.size() < 3) {
      // TODO !!! has to be handled better !!!
      return false;
    }

    // TODO ?? explicit or ??? -> !!! if both is true something went wrong !!!
    return isGroup(combination) || isStreet(combination);
  }

  boolean isGroup(List<Tile> combination) {

    int combinationLength = combination.size();

    if (combinationLength <= 4 && haveSameNumber(combination)) {
      for (int i = 1; i < combinationLength; i++) {

        Tile previous = combination.get(i - 1);
        Tile current = combination.get(i);

        if (previous.getColor().equals(Color.JOKER)
                || current.getColor().equals(Color.JOKER)) {
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

  boolean isStreet(List<Tile> combination) {

    if (haveSameColor(combination) && !haveSameNumber(combination)) {
      for (int i = 1; i < combination.size(); i++) {

        Tile previous = combination.get(i - 1);
        Tile current = combination.get(i);

        if (previous.getNumber().equals(Number.JOKER)
                || current.getNumber().equals(Number.JOKER)) {
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

  private boolean haveSameNumber(List<Tile> combination) {
    Number tempNum = combination.get(0).getNumber();

    if (tempNum.equals(Number.JOKER)) {
      for (Tile tile : combination) {
        Number num = tile.getNumber();

        if (!num.equals(Number.JOKER)) {
          tempNum = num;
          break;
        }
      }
    }

    Number number = tempNum;

    return combination
        .stream()
        .allMatch(
                tile -> tile.getNumber().equals(number) || tile.getNumber().equals(Number.JOKER));
  }

  private boolean haveSameColor(List<Tile> combination) {
    Color tempColor = combination.get(0).getColor();

    if (tempColor.equals(Color.JOKER)) {
      for (Tile tile : combination) {
        Color col = tile.getColor();

        if (!col.equals(Color.JOKER)) {
          tempColor = col;
          break;
        }
      }
    }

    Color color = tempColor;

    return combination
        .stream()
            .allMatch(tile -> tile.getColor().equals(color) || tile.getColor().equals(Color.JOKER));
  }
}
