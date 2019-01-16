package gameinfo;

import gameinfo.util.GITile;

import java.util.ArrayList;
import java.util.List;

class Board {
  private Bag bag;
  private List<List<GITile>> combos;

  Board() {
    this.bag = new Bag();
    this.combos = new ArrayList<>();
  }

  void remove(List<GITile> combo) {
    if (combos.contains(combo)) {
      this.combos.remove(combo);
    }
  }

  List<List<GITile>> getActiveCombos() {
    return this.combos;
  }

  void addCombo(List<GITile> combo) {
    this.combos.add(combo);
  }

  GITile getTileFromBag() {
    return this.bag.takeTile();
  }

  List<GITile> getStackFromBag(int numberOfTiles) {
    return this.bag.takeStack(numberOfTiles);
  }

}
