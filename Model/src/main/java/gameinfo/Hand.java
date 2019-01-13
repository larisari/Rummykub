package gameinfo;

import gameinfo.tile.Tile;

import java.util.ArrayList;
import java.util.List;

class Hand {

  private List<Tile> tilesOnHand;
  private PointsCalculator calculator;

  Hand(PointsCalculator calculator) {
    tilesOnHand = new ArrayList<>();
    this.calculator = calculator;
  }

  int size() {
    return tilesOnHand.size();
  }

  void put(Tile tile) {
    tilesOnHand.add(tile);
  }

  void removeTile(Tile tile) {
    tilesOnHand.remove(tile);
  }

  List<Tile> getTilesOnHand() {
    return tilesOnHand;
  }

  int getPoints() {
    return calculator.getPointsForHand(tilesOnHand);
  }
}
