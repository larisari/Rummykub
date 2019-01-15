package gameinfo;

import gameinfo.util.GITile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class Rules {

  //TODO ? remove class ?

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
    gameFlow.addPlayerToSequence(id);
  }

  void deregisterPlayerBy(Integer id) {
    gameFlow.deregisterPlayerBy(id);
    gameFlow.removePlayerFromSequence(id);
  }

  void startGame() {
    this.gameFlow.startGame();
  }

  void addDistribution() {
    gameFlow.addDistribution();
  }

  void nextPlayersTurn() {
    gameFlow.nextPlayersTurn();
  }

  int getNumberOfPlayers() {
    return gameFlow.getNumberOfPlayers();
  }

  Optional<Player> getPlayerBy(Integer id) {
    return gameFlow.getPlayerBy(id);
  }

  boolean isPlayerExistingBy(Integer id) {
    return gameFlow.playerExists(id);
  }

  Integer getNextPlayerID() {
    return gameFlow.getNextPlayerID();
  }

  boolean isValidPlayerBy(Integer id) {
    return gameFlow.isValidPlayer(id);
  }

  boolean isValid(List<GITile> combination) {
    return combRules.isValid(combination);
  }

  boolean isValid(List<GITile> combination, int minimumPoints) {
    return combRules.isValid(combination, minimumPoints);
  }

  boolean isDistributing() {
    return gameFlow.isDistributing();
  }

  Map<Integer, Player> getAllPlayers() {
    return gameFlow.getPlayers();
  }

}
