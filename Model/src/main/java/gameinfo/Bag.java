package gameinfo;

import tile.Tile;
import tile.util.TileColor;
import tile.util.TileNumber;

import java.util.ArrayList;
import java.util.List;

class Bag {
  private final int SUM_TILES = 106;
  private final int MAX_NUM = 13;
  public List<Tile> tiles;

  Bag() {
    this.tiles = new ArrayList<>(SUM_TILES);
    generateBag();
  }

  private void generateBag() {
    TileNumber num = TileNumber.ONE;

    for (int i = 0; i < MAX_NUM; i++) {
      for (int number = 0; number < 2; number++) {
        this.tiles.add(new Tile(num, TileColor.BLUE));
        this.tiles.add(new Tile(num, TileColor.YELLOW));
        this.tiles.add(new Tile(num, TileColor.RED));
        this.tiles.add(new Tile(num, TileColor.BLACK));
      }

      num = num.next();
    }

    this.tiles.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    this.tiles.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
  }

  public Tile getTile(int position) {
    //position starting at 0
    return this.tiles.get(position);
  }

  public boolean isInBag(Tile tile) {
   //TODO isInBag
    return false;
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
