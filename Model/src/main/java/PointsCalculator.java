import java.util.List;

public class PointsCalculator {

  private PointsCalculator() {}

  public static int getPointsOf(List<Tile> combination) {
    int points = 0;

    for (Tile tile : combination) {
      points += tile.getNumber();
    }

    return points;
  }

}
