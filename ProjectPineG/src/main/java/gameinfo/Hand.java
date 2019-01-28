package gameinfo;

import gameinfo.util.GIPoints;
import gameinfo.util.GITile;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class Hand {

  private List<GITile> tilesOnHand;
  private PointsCalculator calculator;

  private static Logger log = Logger.getLogger(Hand.class.getName());

  Hand(PointsCalculator calculator) {
    tilesOnHand = new ArrayList<>();
    this.calculator = calculator;
  }

  int size() {
    return tilesOnHand.size();
  }

  void put(GITile tile) {
//    log.info("Added the tile " + tile + " to the players hand.");
    tilesOnHand.add(tile);
  }

  void removeTile(GITile tile) {
//    log.info("Removed the tile " + tile + " from the players hand.");
    tilesOnHand.removeIf(i -> i.isEquals(tile));
  }

  List<GITile> getTilesOnHand() {
    return tilesOnHand;
  }

  // Now returns the Points as negative.
  GIPoints calculatePoints() {
    Integer pointsAsInteger = calculator.calculatePointsForHand(tilesOnHand);
    Integer pointsAsNegative = 0 - pointsAsInteger;
//    log.info(
//            "Calculated the points of the players hand. The player has "
//                    + pointsAsNegative
//                    + " points on the hand.");
    return new GIPoints(pointsAsNegative);
  }
}
