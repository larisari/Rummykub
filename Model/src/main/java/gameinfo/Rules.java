package gameinfo;

import tile.Tile;
import java.util.List;

class Rules {

  private GameFlow gameFlow;
  private PointsCalculator pointsCalculator;
  private CombRules combRules;

  Rules() {
    gameFlow = new GameFlow();
    pointsCalculator = new PointsCalculator();
    combRules = new CombRules(pointsCalculator);
  }

  String getNextPlayerID() {
    return gameFlow.getNextPlayerID();
  }

  boolean isValid(List<Tile> combination) {
    return combRules.isValid(combination);
  }

  boolean isValid(List<Tile> combination, int minimumPoints) {
    return combRules.isValid(combination, minimumPoints);
  }

  int getPointsFor(List<Tile> combination) {
    if (combRules.isGroup(combination)) {
      return pointsCalculator.getPointsForGroup(combination);
    } else if (combRules.isStreet(combination)) {
      return pointsCalculator.getPointsForStreet(combination);
    } else {
      // TODO !!! handle better !!!
      throw new IllegalStateException();
    }
  }
}
