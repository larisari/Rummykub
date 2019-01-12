package gameinfo;

import gameinfo.tile.Tile;

import java.util.List;
import java.util.UUID;

class Player {

  private Hand hand;
  private String id;
  private boolean isFirstMove;

  Player() {
    hand = new Hand();
    id = UUID.randomUUID().toString();
    isFirstMove = true;
  }

  Player(String id) {
    this.hand = new Hand();
    this.id = id;
    isFirstMove = true;
  }

  boolean isFirstMove() {
    return isFirstMove;
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

  void remove(List<Tile> tiles) {
    isFirstMove = false;

    for (Tile tile : tiles) {
      hand.removeTile(tile);
    }
  }
}
