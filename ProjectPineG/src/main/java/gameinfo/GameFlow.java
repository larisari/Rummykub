package gameinfo;

import gameinfo.util.GIPoints;
import gameinfo.util.GITuple;

import java.util.*;
import java.util.logging.Logger;

class GameFlow {
  private Map<Integer, Player> players;
  private GameState state;
  private int distributionCounter;
  private List<Integer> playerSequence;
  private Integer currentPlayerIndex;
  private PointsCalculator calculator;

  private static Logger log = Logger.getLogger(GameFlow.class.getName());

  GameFlow(PointsCalculator calculator) {
    this.players = new HashMap<>();
    this.state = GameState.distributing;
    this.distributionCounter = 0;
    this.calculator = calculator;
    this.playerSequence = new ArrayList<>();
    this.currentPlayerIndex = 0;
  }

  void registerBy(Integer id) {
    players.put(id, new Player(id, calculator));
    playerSequence.add(id);
    log.info("Registered Player with id " + id + ".");
  }

  void deregisterBy(Integer id) {
    players.remove(id);
    playerSequence.remove(id);
    log.info("Deregistered Player with id " + id + ".");
  }

  boolean isPlayerExistingBy(Integer id) {
    return getPlayerBy(id).isPresent();
  }

  void startGame() {
    this.state = GameState.distributing;
    log.info("Game started.");
  }

  boolean isValidPlayerBy(Integer id) {
    return players.get(id).getId().equals(id);
  }

  Map<Integer, Player> getPlayers() {
    return this.players;
  }

  Optional<Player> getPlayerBy(Integer id) {
    if (players.containsKey(id)) {
      return Optional.of(players.get(id));
    } else {
      log.info("ID " + id + " is not registered in the model.");
      return Optional.empty();
    }
  }

  Integer getCurrentPlayerId() {
    return playerSequence.get(currentPlayerIndex);
  }

  Integer getNextPlayerID() {
    if (currentPlayerIndex < playerSequence.size() - 1) {
      return playerSequence.get(currentPlayerIndex + 1);
    } else {
      return playerSequence.get(0);
    }
  }

  void nextPlayersTurn() {
    if (currentPlayerIndex < playerSequence.size() - 1) {
      currentPlayerIndex++;
    } else {
      currentPlayerIndex = 0;
    }

    Integer currentPlayerId = playerSequence.get(currentPlayerIndex);
    players.get(currentPlayerId).resetMadeMove();

    log.info("Next Players turn. Now its Player " + currentPlayerId + "s turn.");
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

  Optional<List<GITuple<Integer, GIPoints>>> getPlayerPoints() {
    if (!players.isEmpty()) {
      List<GITuple<Integer, GIPoints>> allPlayersHands = new ArrayList<>();
      for (Map.Entry<Integer, Player> player : players.entrySet()) {
        GITuple<Integer, GIPoints> playersHand =
            new GITuple<>(player.getValue().getId(), player.getValue().calculatePointsOfHand());
        allPlayersHands.add(playersHand);
      }
      return Optional.of(allPlayersHands);
    } else {
      return Optional.empty();
    }
  }

  boolean hasMadeMoveBy(Integer id) {
    log.info("Player with the id " + id + " has made a move.");
    return players.get(id).hasMadeMove();
  }

  //  private void removePlayerFromSequence(Integer id) {
  //    //    playerSequence.removeIf(i -> i.equals(id));
  //  }
  //
  //  private void addPlayerToSequence(Integer id) {
  //    // current order: time of registration
  //
  //    // TODO modify the order according to will
  //
  //    //    if (players.containsKey(id)) {
  //    //      playerSequence.add(id);
  //    //    }
  //  }
}
