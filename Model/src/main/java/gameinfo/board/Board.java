package gameinfo.board;

import tile.Tile;

import java.util.List;

public interface Board {

  List<List<Tile>> getActiveCombos();

  void addCombo(List<Tile> combo);

  List<Tile> getCombo(int index);

  Tile getTileFromBag();

  List<Tile> getStackFromBag();

}
