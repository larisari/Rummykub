package gameinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class GameFlow {
  private List<Player> players;
  private GameState state;

  GameFlow() {
    this.players = new ArrayList<>();
    this.state = GameState.distributing;
  }

  void registerPlayerBy(String id) {
    players.add(new Player(id));
  }

  boolean playerExists(String id) {
    return getPlayerBy(id).isPresent();
  }

  void deregisterPlayerBy(String id) {
    if (getPlayerBy(id).isPresent()) {
      players.remove(getPlayerBy(id).get());
    }
    else {
      throw new IllegalArgumentException("No player with id\"" + id + "\" " +
          "found.");
    }
  }

  List<Player> getPlayers() {
    return this.players;
  }

  Optional<Player> getPlayerBy(String id) {
    for (int i = 0; i < players.size(); i++) {
      if (players.get(i).getId().equals(id)) {
        return Optional.of(players.get(i));
      }
    }
    return Optional.empty();
  }

  String getNextPlayerID() {
    return null;
  }


}
