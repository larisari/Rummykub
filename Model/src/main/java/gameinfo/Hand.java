package gameinfo;

import gameinfo.tile.Tile;

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

  @Override
  public String toString() {
    // TODO
    return "";
  }
}
