package gameinfo;

import tile.Tile;

import java.util.ArrayList;
import java.util.List;

class Hand {

  private List<Tile> tilesOnHand;

  Hand() {
    tilesOnHand = new ArrayList<>();
  }

  int size() {
    return tilesOnHand.size();
  }

  void put(Tile tile) {
    tilesOnHand.add(tile);
  }

  void removeTile(Tile tile) {
    tilesOnHand.remove(tile);
  }

  List<Tile> getTilesOnHand() {
    return tilesOnHand;
  }

//  // why exactly do we need this ???
//  Optional<Tile> pickTile(int position) {
//    Tile tileAtPosition = tilesOnHand.get(position);
//
//    if (tileAtPosition != null) {
//      return Optional.of(tileAtPosition);
//    } else {
//      return Optional.empty();
//    }
//  }
}
