package gameinfo;

import tile.Tile;

import java.util.List;
import java.util.Optional;

public interface GameInfo {

  String getNextPlayerID();

  Optional<List<Tile>> getAllTilesBy(String id);

  List<Tile> getStackFromBag();

  Tile getOneTileFromBag();

  boolean isValidMove(List<Tile> combination);

  int getPointsForMove(List<Tile> combination);
}
