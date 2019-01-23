package gameinfo;

import gameinfo.util.GITile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

class Board {
  private Bag bag;
  private List<List<GITile>> combos;

  private static Logger log = Logger.getLogger(Board.class.getName());

  Board() {
    this.bag = new Bag();
    this.combos = new ArrayList<>();
  }

  void remove(List<GITile> combination) {
    this.combos.remove(combination);
  }

  void clear() {
    log.info("Cleared Board.");
    this.combos = new ArrayList<>();
  }

  List<List<GITile>> getActiveCombos() {
    return this.combos;
  }

  void addCombo(List<GITile> combo) {
    this.combos.add(combo);
    log.info("Added combination: " + combo + " to the board.");
  }

  GITile drawRandomTile() {
    return this.bag.drawRandomTile();
  }

  List<GITile> drawRandomStackWith(int numberOfTiles) {
    return this.bag.drawRandomStackWith(numberOfTiles);
  }

  Optional<GITile> getTileFromBag(GITile tile) {
    if (bag.takeTile(tile).isPresent()) {
      return Optional.of(bag.takeTile(tile).get());
    } else {
      return Optional.empty();
    }
  }

  List<GITile> getStackFromBag(int numberOfTiles, List<GITile> customStack) {
    return this.bag.takeStack(numberOfTiles, customStack);
  }
}
