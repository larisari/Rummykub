package gameinfo;

import tile.Tile;
import java.util.List;
import java.util.Optional;

class Rules {

  private GameFlow gameFlow;
  private PointsCalculator pointsCalculator;
  private CombRules combRules;

  Rules() {
    gameFlow = new GameFlow();
    pointsCalculator = new PointsCalculator();
    combRules = new CombRules(pointsCalculator);
  }

  void registerBy(String id) {
    gameFlow.registerPlayerBy(id);
  }

  void deregisterPlayerBy(String id) {
    gameFlow.deregisterPlayerBy(id);
  }

  boolean isPlayerExistingBy(String id) {
    return gameFlow.playerExists(id);
  }

  List<Player> getAllPlayers() {
    return gameFlow.getPlayers();
  }

  Optional<Player> getPlayerBy(String id) {
    return gameFlow.getPlayerBy(id);
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
