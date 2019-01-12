package gameinfo;

import tile.Tile;

import java.util.List;
import java.util.Optional;

public interface GIGameInfo {

  void registerBy(String id);

  void deregisterBy(String id);

  // start()

  String getNextPlayerId();

  // just for test TODO: REMOVE
  List<String> getAllPlayerIds();

  Optional<List<Tile>> getAllTilesBy(String id);

  Optional<List<Tile>> getStackFor(String id);

  Optional<Tile> getTileFor(String id);

  // just for the test TODO: REMOVE
  // for the real function one needs to pass the id.
  // also a problem is on how to add functionality to combine tiles on board.
  boolean isValidMove(List<Tile> combination);

  // real function has to be named differently -> playCombination(), ...
  boolean isValidMove(List<Tile> tilesFromHand, List<Tile> tilesFromBoard, List<List<Tile>> newCombination);

  int getPointsForMove(List<Tile> combination);

}
