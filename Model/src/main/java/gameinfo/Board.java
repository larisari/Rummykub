package gameinfo;

import tile.Tile;

import java.util.ArrayList;
import java.util.List;

class Board {
  private Bag bag;
  private List<List<Tile>> combos;

  Board() {
    this.bag = new Bag(); //filled Bag
    this.combos = new ArrayList<>(); //empty List of Combos
  }

  List<List<Tile>> getActiveCombos() {
    return this.combos;
  }

  void addCombo(List<Tile> combo) {
    this.combos.add(combo);
  }

  List<Tile> getCombo(int index) {
    return this.combos.get(index);
  }

  Tile getTileFromBag() {
    return null;
  }

  List<Tile> getStackFromBag() {
    return null;
  }
}
