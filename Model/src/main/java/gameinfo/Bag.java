package gameinfo;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GITile;

import java.util.ArrayList;
import java.util.List;

class Bag {
  private final int SUM_TILES = 106;
  private final int MAX_NUM = 13;
  private List<GITile> tiles;

  Bag() {
    this.tiles = new ArrayList<>(SUM_TILES);
    generateBag();
  }

  void generateBag() {
    GINumber num = GINumber.ONE;

    for (int i = 0; i < MAX_NUM; i++) {
      for (int number = 0; number < 2; number++) {
        this.tiles.add(new GITile(num, GIColor.BLUE));
        this.tiles.add(new GITile(num, GIColor.YELLOW));
        this.tiles.add(new GITile(num, GIColor.RED));
        this.tiles.add(new GITile(num, GIColor.BLACK));
      }

      num = num.next();
    }

    this.tiles.add(new GITile(GINumber.JOKER, GIColor.JOKER));
    this.tiles.add(new GITile(GINumber.JOKER, GIColor.JOKER));
  }

  GITile takeTile() {
    int position = (int) (Math.random() * (this.tiles.size() - 1));
    GITile tile = this.tiles.get(position);
    this.tiles.remove(position);
    return tile;
  }

  List<GITile> takeStack(int numberOfTiles) {
    List<GITile> stack = new ArrayList<>();
    for (int i = 0; i < numberOfTiles; i++) {
      int index = (int) (Math.random() * (this.tiles.size() - 1));
      stack.add(this.tiles.get(index));
      this.tiles.remove(index);
    }
    return stack;
  }

}

