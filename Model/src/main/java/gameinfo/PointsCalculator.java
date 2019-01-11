package gameinfo;

import tile.Tile;
import tile.util.TileNumber;
import java.util.List;

// TODO !!! MAKE PACKAGE_PRIVATE !!!
class PointsCalculator {

  PointsCalculator() {
  }

  int getPointsForStreet(List<Tile> street) {
    int points = 0;

    for (Tile tile : street) {
      TileNumber tilePoints = tile.getNumber();

      if (!tilePoints.equals(TileNumber.JOKER)) {
        points += tilePoints.value();
      } else {
        // tile is a Joker
        int indexOfJoker = street.indexOf(tile);

        if (indexOfJoker > 0) {
          TileNumber pointsOfPrevTile = street.get(indexOfJoker - 1).getNumber();
          points += pointsOfPrevTile.next().value();
        } else {
          TileNumber pointsOfNextTile = street.get(indexOfJoker + 1).getNumber();
          points += pointsOfNextTile.previous().value();
        }
      }
    }

    return points;
  }

  int getPointsForGroup(List<Tile> group) {
    int points = 0;

    for (Tile tile : group) {
      TileNumber numberOfTile = tile.getNumber();

      if (!numberOfTile.equals(TileNumber.JOKER)) {
        points = numberOfTile.value() * group.size();
        break;
      }
    }

    return points;
  }
}