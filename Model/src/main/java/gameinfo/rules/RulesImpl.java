package gameinfo.rules;

import tile.Tile;
import java.util.List;

class RulesImpl implements Rules {

  private GameFlow gameFlow;
  private PointsCalculator pointsCalculator;
  private CombRules combRules;

  RulesImpl() {
    gameFlow = new GameFlow();
    pointsCalculator = new PointsCalculator();
    combRules = new CombRules(pointsCalculator);
  }

  @Override
  public String getNextPlayerID() {
    return gameFlow.getNextPlayerID();
  }

  @Override
  public boolean isValid(List<Tile> combination) {
    return combRules.isValid(combination);
  }

  @Override
  public boolean isValid(List<Tile> combination, int minimumPoints) {
    return combRules.isValid(combination, minimumPoints);
  }

  @Override
  public int getPointsFor(List<Tile> combination) {
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
