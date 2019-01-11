package gameinfo;

import gameinfo.player.Player;
import tile.Tile;

import java.util.List;
import java.util.Optional;

public interface GIGameInfo {

  String getNextPlayerID();

  Optional<List<Tile>> getAllTilesBy(String id);

  List<Tile> getStackFromBag();

  Tile getOneTileFromBag();

  boolean isValidMove(List<Tile> combination);

  int getPointsForMove(List<Tile> combination);

  void registerBy(String id);

  void deregisterBy(String id);
}
