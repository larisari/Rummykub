
import java.util.ArrayList;
import java.util.Arrays;
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
    List<Tile.Colors> colors = Arrays.asList(Tile.Colors.values());
    for (int i = 1; i < MAX_NUM+1; i++) {
      for(int j = 0; j < colors.size()-1; j++) {
        Tile tile = new Tile(i,colors.get(j));
        this.tiles.add(tile);
        this.tiles.add(tile);
      }
    }
    this.tiles.add(new Tile(0, Tile.Colors.JOKER));
    this.tiles.add(new Tile(0, Tile.Colors.JOKER));
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
