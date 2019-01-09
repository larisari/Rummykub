import java.util.ArrayList;
import java.util.List;

public class Bag {
  private final int SUM_TILES = 106;
  private final int MAX_NUM = 13;
  public List<Tile> tiles;

  public Bag() {
    this.tiles = new ArrayList<>(SUM_TILES);
    generateBag();
  }

  public Tile getTile(int position) {
    //position starting at 0
    return this.tiles.get(position);
  }

  public boolean isInBag(Tile tile) {
   //TODO isInBag
    return false;
  }

  public void generateBag() {
    Number num = Number.ONE;

    for (int i = 0; i < MAX_NUM; i++) {
      for (int number = 0; number < 2; number++) {
        this.tiles.add(new Tile(num, Color.BLUE));
        this.tiles.add(new Tile(num, Color.YELLOW));
        this.tiles.add(new Tile(num, Color.RED));
        this.tiles.add(new Tile(num, Color.BLACK));
      }

      num = num.next();
    }

    this.tiles.add(new Tile(Number.JOKER, Color.JOKER));
    this.tiles.add(new Tile(Number.JOKER, Color.JOKER));
  }

  public void remove(Tile tile) {
    this.tiles.remove(tile);
  }

  @Override public String toString() {
    StringBuilder builder = new StringBuilder();
    for (Tile tile : this.tiles) {
      builder.append(tile.toString()).append("\n");
    }
    return builder.toString();
  }

}
