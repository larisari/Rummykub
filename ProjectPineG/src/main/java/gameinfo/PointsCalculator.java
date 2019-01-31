package gameinfo;

import gameinfo.util.GINumber;
import gameinfo.util.GITile;

import java.util.List;

/**
 * This class provides methods to calculate the points for a street, group or any list of tiles. To
 * be sure the points are calculated correctly only valid combinations should be passed with the
 * methods. To sum all the tiles in a list use the method calculatePointsForHand.
 *
 * <p>The joker takes over the value of the stone he replaces in the combination.
 */
class PointsCalculator {

  PointsCalculator() {}

  int calculatePointsForStreet(List<GITile> street) {
    int points = 0;

    for (GITile tile : street) {
      GINumber tilePoints = tile.getNumber();

      if (!tilePoints.equals(GINumber.JOKER)) {
        points += tilePoints.value();
      } else {
        // tile is a Joker
        int indexOfJoker = street.indexOf(tile);

        if (indexOfJoker > 0) {
          GINumber pointsOfPrevTile = street.get(indexOfJoker - 1).getNumber();
          points += pointsOfPrevTile.next().value();
        } else {
          GINumber pointsOfNextTile = street.get(indexOfJoker + 1).getNumber();
          points += pointsOfNextTile.previous().value();
        }
      }
    }

    return points;
  }

  int calculatePointsForGroup(List<GITile> group) {
    int points = 0;

    for (GITile tile : group) {
      GINumber numberOfTile = tile.getNumber();

      if (!numberOfTile.equals(GINumber.JOKER)) {
        points = numberOfTile.value() * group.size();
        break;
      }
    }

    return points;
  }

  int calculatePointsForHand(List<GITile> hand) {
    int points = 0;

    for (GITile tile : hand) {
      points += tile.getNumber().value();
    }

    return points;
  }
}
