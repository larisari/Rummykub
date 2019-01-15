package gameinfo;

import gameinfo.util.GIPoints;
import gameinfo.util.GITile;

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

  List<GITile> getTilesOnHand() {
    return hand.getTilesOnHand();
  }

  Integer getId() {
    return id;
  }

  void put(GITile tile) {
    hand.put(tile);
  }

  void put(List<GITile> stack) {
    for (GITile tile : stack) {
      hand.put(tile);
    }
  }

  void remove(List<GITile> tiles) {
    isFirstMove = false;

    for (GITile tile : tiles) {
      hand.removeTile(tile);
    }
  }

  GIPoints getPointsOfHand() {
    return hand.getPoints();
  }
}
