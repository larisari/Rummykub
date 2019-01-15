package gameinfo;

import gameinfo.tile.Tile;

import java.util.List;

class Player {

  private Hand hand;
  private Integer id;
  private boolean isFirstMove;

  Player(Integer id, PointsCalculator calculator) {
    this.hand = new Hand(calculator);
    this.id = id;
    isFirstMove = true;
  }

  boolean isFirstMove() {
    return isFirstMove;
  }

  List<Tile> getTilesOnHand() {
    return hand.getTilesOnHand();
  }

  Integer getId() {
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

  int getPointsOfHand() {
    return hand.getPoints();
  }
}
