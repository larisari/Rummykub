package gameinfo.rules;

import java.util.ArrayList;
import java.util.List;

class GameFlow {
  private List<String> players;
  private GameState state;
  private int counter;


  GameFlow() {
    this.players = new ArrayList<>();
    this.state = GameState.distributing;
  }

  String getPlayerID(int position) {
    return players.get(position);
  }

  String getNextPlayerID() {
    return null;
  }
}
