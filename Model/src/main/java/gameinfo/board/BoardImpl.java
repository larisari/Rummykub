package gameinfo.board;

import tile.Tile;

import java.util.ArrayList;
import java.util.List;

class BoardImpl implements Board{
  Bag bag;
  List<List<Tile>> combos;

  BoardImpl() {
    this.bag = new Bag(); //filled Bag
    this.combos = new ArrayList<>(); //empty List of Combos
  }

  @Override
  public List<List<Tile>> getActiveCombos() {
    return this.combos;
  }

  @Override
  public void addCombo(List<Tile> combo) {
    this.combos.add(combo);
  }

  @Override
  public List<Tile> getCombo(int index) {
    return this.combos.get(index);
  }

  @Override
  public Tile getTileFromBag() {
    return null;
  }

  @Override
  public List<Tile> getStackFromBag() {
    return null;
  }
}
