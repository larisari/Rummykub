import java.util.List;

public class PointsCalculator {

  private PointsCalculator() {}

  /**
   * has to be a valid combination.
   *
   * @param combination the valid combination
   * @return
   */
  public static int getPointsOf(List<Tile> combination) {
    int points = 0;

    if (CombRules.isStreet(combination)) {
      for (Tile tile : combination) {
        TileNumber tilePoints = tile.getNumber();

        if (!tilePoints.equals(TileNumber.JOKER)) {
          points += tilePoints.value();
        } else {
          // tile is a Joker
          int indexOfJoker = combination.indexOf(tile);

          if (indexOfJoker > 0) {
            TileNumber pointsOfPrevTile = combination.get(indexOfJoker - 1).getNumber();
            points += pointsOfPrevTile.next().value();
          } else {
            TileNumber pointsOfNextTile = combination.get(indexOfJoker + 1).getNumber();
            points += pointsOfNextTile.previous().value();
          }
        }
      }
    }

    if (CombRules.isGroup(combination)) {
       for (Tile tile : combination) {
          TileNumber numberOfTile = tile.getNumber();

          if (!numberOfTile.equals(TileNumber.JOKER)) {
            points = numberOfTile.value() * combination.size();
            break;
          }
        }
      }

    return points;
  }
}

// 1 2 3 J

// J 3 4 5

// 5 5 J 5

// 7 7 7 J
