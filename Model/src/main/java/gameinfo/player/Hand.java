package gameinfo.player;

import tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Hand {

  private List<Tile> tilesOnHand;

  Hand() {
    tilesOnHand = new ArrayList<>();
  }

  void putTile(Tile tile) {
    tilesOnHand.add(tile);
  }

  boolean removeTile(Tile tile) {
    if (tilesOnHand.contains(tile)) {
      tilesOnHand.remove(tile);
      return true;
    } else {
      return false;
    }
  }

  List<Tile> getTilesOnHand() {
    return tilesOnHand;
  }

  // why exactly do we need this ???
  Optional<Tile> pickTile(int position) {
    Tile tileAtPosition = tilesOnHand.get(position);

    if (tileAtPosition != null) {
      return Optional.of(tileAtPosition);
    } else {
      return Optional.empty();
    }
  }
}
