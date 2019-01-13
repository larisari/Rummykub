package gameinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class GameFlow {
  private List<Player> players;
  private GameState state;
  private int currentPlayerIndex;
  private int distributionCounter;

  GameFlow() {
    this.players = new ArrayList<>();
    this.state = GameState.distributing;
    this.currentPlayerIndex = 0;
    this.distributionCounter = 0;
  }

  void registerPlayerBy(String id) {
    players.add(new Player(id));
  }

  boolean playerExists(String id) {
    return getPlayerBy(id).isPresent();
  }

  void deregisterPlayerBy(String id) {
    for (int i = 0; i < getPlayers().size(); i++) {
      if (players.get(i).getId().equals(id)) {
        this.players.remove(i);
      }
    }
  }

  void startGame() {
    this.state = GameState.distributing;
  }

  boolean isValidPlayer(String id) {
    return players.get(currentPlayerIndex).getId().equals(id);
  }

  List<Player> getPlayers() {
    return this.players;
  }

  Optional<Player> getPlayerBy(String id) {
    for (Player player : this.players) {
      if (player.getId().equals(id)) {
        return Optional.of(player);
      }
    }
    return Optional.empty();
  }

  String getNextPlayerID() {
    if (currentPlayerIndex == this.players.size() - 1) {
      return this.players.get(0).getId();
    }
    else {
      return this.players.get(currentPlayerIndex + 1).getId();
    }
  }

  void nextPlayer() {
    if (currentPlayerIndex == this.players.size() - 1) {
      this.currentPlayerIndex = 0;
    } else {
      this.currentPlayerIndex++;
    }
  }

  boolean isDistributing() {
    return state.equals(GameState.distributing);
  }

  int getNumberOfPlayers() {
    return this.players.size();
  }

  void addDistribution() {
    this.distributionCounter++;
    if (getDistributionCounter() == getNumberOfPlayers()) {
      running();
    }
  }

  void running() {
    this.state = GameState.running;
  }

  int getDistributionCounter() {
    return this.distributionCounter;
  }



}
