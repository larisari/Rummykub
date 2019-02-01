package gameinfo;

import gameinfo.util.GIPoints;
import gameinfo.util.GITile;

import java.util.List;

/**
 * This class represents a player. It holds the current hand, the id and an age. It also keeps track
 * of some meta information, like if the player has already made its first move or if he did make a
 * move at all.
 */
class Player implements Comparable<Player> {

  private Hand hand;
  private Integer id;
  private boolean isFirstMove;
  private boolean moved;
  private int age;

  Player(Integer id, PointsCalculator calculator) {
    this.hand = new Hand(calculator);
    this.id = id;
    this.age = 0;
    isFirstMove = true;
  }

  void setAge(int age) {
    this.age = age;
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

  void resetMadeMove() {
    moved = false;
  }

  void madeMove() {
    moved = true;
  }

  boolean hasMadeMove() {
    return moved;
  }

  GIPoints calculatePointsOfHand() {
    return hand.calculatePoints();
  }

  @Override
  public int compareTo(Player player) {
    return age - player.age;
  }
}
