package gameinfo;

import tile.Tile;

import java.util.List;
import java.util.UUID;

class Player {

  private Hand hand;
  private String id;

  Player() {
    hand = new Hand();
    id = UUID.randomUUID().toString();
  }

  Player(String id) {
    this.hand = new Hand();
    this.id = id;
  }

  List<Tile> getTilesOnHand() {
    return hand.getTilesOnHand();
  }

  String getId() {
    return id;
  }

  void put(Tile tile) {
    hand.put(tile);
  }

  void put(List<Tile> stack) {
    for (Tile tile : stack) {
      hand.put(tile);
    }
  }

  boolean removeTile(Tile tile) {
    return hand.removeTile(tile);
  }
}
