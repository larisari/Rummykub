package gameinfo;

import tile.Tile;

import java.util.List;
import java.util.Optional;

public interface GIGameInfo {

  String getNextPlayerId();

  // just for test TODO: REMOVE
  List<String> getAllPlayerIds();

  Optional<List<Tile>> getAllTilesBy(String id);

  Optional<List<Tile>> getStackFor(String id);

  Optional<Tile> getTileFor(String id);

  boolean isValidMove(List<Tile> combination);

  int getPointsForMove(List<Tile> combination);

  void registerBy(String id);

  void deregisterBy(String id);
}
