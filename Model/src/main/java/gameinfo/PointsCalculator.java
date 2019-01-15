package gameinfo;

import gameinfo.util.GINumber;
import gameinfo.util.GITile;

import java.util.List;

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
//      GINumber numberOfTile = tile.getNumber();
//
//      if (numberOfTile.equals(GINumber.JOKER)) {
//        points += 20;
//      } else {
//        points += numberOfTile.value();
//      }
    }

    return points;
  }
}
