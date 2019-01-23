package gameinfo;

import gameinfo.util.GITile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

  Optional<GITile> getTileFromBag(GITile tile) {
    if (bag.takeTile(tile).isPresent()) {
      return Optional.of(bag.takeTile(tile).get());
    }
    else {
      return Optional.empty();
    }
  }

  List<GITile> getStackFromBag(int numberOfTiles) {
    return this.bag.takeStack(numberOfTiles);
  }

List<GITile> getStackFromBag(int numberOfTiles, List<GITile> customStack) {
    return this.bag.takeStack(numberOfTiles,customStack);
  }

}
