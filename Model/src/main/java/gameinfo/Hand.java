package gameinfo;

import gameinfo.util.GIPoints;
import gameinfo.util.GITile;

import java.util.ArrayList;
import java.util.List;

class Hand {

  private List<GITile> tilesOnHand;
  private PointsCalculator calculator;

  Hand(PointsCalculator calculator) {
    tilesOnHand = new ArrayList<>();
    this.calculator = calculator;
  }

  int size() {
    return tilesOnHand.size();
  }

  void put(GITile tile) {
    tilesOnHand.add(tile);
  }

  void removeTile(GITile tile) {
    tilesOnHand.remove(tile);
  }

  List<GITile> getTilesOnHand() {
    return tilesOnHand;
  }

  // Now returns the Points as negative.
  GIPoints calculatePoints() {
    Integer pointsAsInteger = calculator.calculatePointsForHand(tilesOnHand);
    Integer pointsAsNegative = 0 - pointsAsInteger;
    return new GIPoints(pointsAsNegative);
  }
}
