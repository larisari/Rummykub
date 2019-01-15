package gameinfo;

import gameinfo.util.GITile;

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
    return gameFlow.isValidPlayer(id);
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

  boolean isValid(List<GITile> combination) {
    return combRules.isValid(combination);
  }

  boolean isValid(List<GITile> combination, int minimumPoints) {
    return combRules.isValid(combination, minimumPoints);
  }

  void nextPlayersTurn() {
    gameFlow.nextPlayersTurn();
  }

  int getNumberOfPlayers() {
    return gameFlow.getNumberOfPlayers();
  }

  void addDistribution() {
    gameFlow.addDistribution();
  }
}
