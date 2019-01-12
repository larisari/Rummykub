package gameinfo;

import gameinfo.tile.Tile;
import gameinfo.tile.util.Number;

import java.util.List;

class PointsCalculator {

  PointsCalculator() {
  }

  int getPointsForStreet(List<Tile> street) {
    int points = 0;

    for (Tile tile : street) {
      Number tilePoints = tile.getNumber();

      if (!tilePoints.equals(Number.JOKER)) {
        points += tilePoints.value();
      } else {
        // gameinfo.tile is a Joker
        int indexOfJoker = street.indexOf(tile);

        if (indexOfJoker > 0) {
          Number pointsOfPrevTile = street.get(indexOfJoker - 1).getNumber();
          points += pointsOfPrevTile.next().value();
        } else {
          Number pointsOfNextTile = street.get(indexOfJoker + 1).getNumber();
          points += pointsOfNextTile.previous().value();
        }
      }
    }

    return points;
  }

  int getPointsForGroup(List<Tile> group) {
    int points = 0;

    for (Tile tile : group) {
      Number numberOfTile = tile.getNumber();

      if (!numberOfTile.equals(Number.JOKER)) {
        points = numberOfTile.value() * group.size();
        break;
      }
    }

    return points;
  }
}