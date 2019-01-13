package gameinfo;

import gameinfo.tile.Tile;

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

  void startGame() {
    this.gameFlow.startGame();
  }

  boolean isDistributing() {
    return gameFlow.isDistributing();
  }

  boolean isPlayerExistingBy(String id) {
    return gameFlow.playerExists(id);
  }

  boolean isValidPlayer(String id) {
    return this.gameFlow.isValidPlayer(id);
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

  void nextPlayer() {
    this.gameFlow.nextPlayer();
  }

  int getNumberOfPlayers() {
    return this.gameFlow.getNumberOfPlayers();
  }

  void addDistribution() {
    gameFlow.addDistribution();
  }



}
