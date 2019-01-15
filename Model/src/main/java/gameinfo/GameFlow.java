package gameinfo;
import java.util.*;

class GameFlow {
  private Map<Integer, Player> players;
  private GameState state;
  private int distributionCounter;

  private PointsCalculator calculator;

  GameFlow(PointsCalculator calculator) {
    this.players = new HashMap<>();
    this.state = GameState.distributing;
    this.distributionCounter = 0;
    this.calculator = calculator;
  }

  void registerPlayerBy(Integer id) {
    players.put(id, new Player(id, calculator));
  }

  boolean playerExists(Integer id) {
    return getPlayerBy(id).isPresent();
  }

  void deregisterPlayerBy(Integer id) {
    players.remove(id);
  }

  void startGame() {
    this.state = GameState.distributing;
  }

  boolean isValidPlayer(Integer id) {
    return players.get(id).getId().equals(id);
  }

  Map<Integer, Player> getPlayers() {
    return this.players;
  }

  Optional<Player> getPlayerBy(Integer id) {
    if (players.containsKey(id)) {
      return Optional.of(players.get(id));
    }
    else {
      return Optional.empty();
    }
  }

  Integer getNextPlayerID() {
    //TODO implement
    return null;
  }

  void nextPlayersTurn() {
//    if (currentPlayerIndex == this.players.size() - 1) {
//      this.currentPlayerIndex = 0;
//    } else {
//      this.currentPlayerIndex++;
//    }
    //TODO implement
  }

  boolean isDistributing() {
    return state.equals(GameState.distributing);
  }

  int getNumberOfPlayers() {
    return this.players.size();
  }

  void addDistribution() {
    this.distributionCounter++;
    if (distributionCounter == getNumberOfPlayers()) {
      this.state = GameState.running;
    }
  }


}
