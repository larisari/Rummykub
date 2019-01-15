package gameinfo;

import gameinfo.tile.Tile;

import java.util.ArrayList;
import java.util.List;

class Board {
  private Bag bag;
  private List<List<Tile>> combos;

  Board() {
    this.bag = new Bag();
    this.combos = new ArrayList<>();
  }

  void remove(List<Tile> combo) {
    if (combos.contains(combo)) {
      this.combos.remove(combo);
    }
  }

  List<List<Tile>> getActiveCombos() {
    return this.combos;
  }

  void addCombo(List<Tile> combo) {
    this.combos.add(combo);
  }

  Tile getTileFromBag() {
    return this.bag.takeTile();
  }

  List<Tile> getStackFromBag(int numberOfTiles) {
    return this.bag.takeStack(numberOfTiles);
  }

}
