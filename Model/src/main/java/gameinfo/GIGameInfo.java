package gameinfo;

import gameinfo.tile.Tile;

import java.util.List;
import java.util.Optional;

public interface GIGameInfo {

  void registerBy(String id);

  void deregisterBy(String id);

  // start()

  String getNextPlayerId();

  // just for test TODO: REMOVE
  List<String> getAllPlayerIds();

  boolean play(List<Tile> combination, String id);

  boolean play(List<Tile> tilesFromHand, List<Tile> tilesFromBoard, List<List<Tile>> newCombinations, String id);

  Optional<List<Tile>> getAllTilesBy(String id);

  Optional<List<Tile>> getStackFor(String id);

  Optional<Tile> getTileFor(String id);

  int getPointsForMove(List<Tile> combination);
}
