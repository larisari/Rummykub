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

  int getPoints() {
    int points = 0;

    for (Tile tile : tilesOnHand) {
      points += tile.getNumber().value();
    }

    return points;
  }
}
