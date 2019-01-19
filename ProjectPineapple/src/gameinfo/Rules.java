package gameinfo;

import gameinfo.util.GIPoints;
import gameinfo.util.GITile;
import gameinfo.util.GITuple;
import org.w3c.dom.stylesheets.LinkStyle;

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

  boolean isValid(List<List<GITile>> combinations) {
    return combRules.isValid(combinations);
  }

  boolean isValid(List<List<GITile>> combinations, int minimumPoints) {
    return combRules.isValid(combinations, minimumPoints);
  }

  boolean isDistributing() {
    return gameFlow.isDistributing();
  }

  Map<Integer, Player> getAllPlayers() {
    return gameFlow.getPlayers();
  }

  Optional<List<GITuple<Integer, GIPoints>>> getPlayerPoints() {
    return gameFlow.getPlayerPoints();
  }

}
