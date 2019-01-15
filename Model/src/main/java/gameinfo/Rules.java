package gameinfo;

import gameinfo.tile.Tile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class Rules {

  private GameFlow gameFlow;
  private PointsCalculator pointsCalculator;
  private CombRules combRules;

  Rules() {
    pointsCalculator = new PointsCalculator();
    gameFlow = new GameFlow(pointsCalculator);
    combRules = new CombRules(pointsCalculator);
  }

  void registerBy(Integer id) {
    gameFlow.registerPlayerBy(id);
  }

  void deregisterPlayerBy(Integer id) {
    gameFlow.deregisterPlayerBy(id);
  }

  void startGame() {
    this.gameFlow.startGame();
  }

  boolean isDistributing() {
    return gameFlow.isDistributing();
  }

  boolean isPlayerExistingBy(Integer id) {
    return gameFlow.playerExists(id);
  }

  boolean isValidPlayerBy(Integer id) {
    return this.gameFlow.isValidPlayer(id);
  }

  Map<Integer, Player> getAllPlayers() {
    return gameFlow.getPlayers();
  }

  Optional<Player> getPlayerBy(Integer id) {
    return gameFlow.getPlayerBy(id);
  }

  Integer getNextPlayerID() {
    return gameFlow.getNextPlayerID();
  }

  boolean isValid(List<Tile> combination) {
    return combRules.isValid(combination);
  }

  boolean isValid(List<Tile> combination, int minimumPoints) {
    return combRules.isValid(combination, minimumPoints);
  }

//  int getPointsFor(List<Tile> combination) {
//    if (combRules.isGroup(combination)) {
//      return pointsCalculator.getPointsForGroup(combination);
//    } else if (combRules.isStreet(combination)) {
//      return pointsCalculator.getPointsForStreet(combination);
//    } else {
//      // TODO !!! handle better !!!
//      throw new IllegalStateException();
//    }
//  }

  void nextPlayersTurn() {
    this.gameFlow.nextPlayersTurn();
  }

  int getNumberOfPlayers() {
    return this.gameFlow.getNumberOfPlayers();
  }

  void addDistribution() {
    gameFlow.addDistribution();
  }

}
