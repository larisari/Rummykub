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

  void removeMultiple(List<List<GITile>> combinations) {
    for (List<GITile> combination : combinations) {
      remove(combination);
    }
  }

  void remove(List<GITile> combo) {
    log.info("Remove the combination " + combo + "from the board.");
    for (List<GITile> combination : combos) {
      if (combination.size() == combo.size()) {
        log.info("Combinations have the same size.");
        if (containSameElements(combo, combination)) {
          log.info("Combinations contains the same elements.");
          combos.remove(combination);
          return;
        }
      }
    }
  }

  private boolean containSameElements(List<GITile> lhs, List<GITile> rhs) {
    for (int i = 0; i < lhs.size(); i++) {
      if (!lhs.get(i).toString().equals(rhs.get(i).toString())) {
        return false;
      }
    }
    return true;
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

