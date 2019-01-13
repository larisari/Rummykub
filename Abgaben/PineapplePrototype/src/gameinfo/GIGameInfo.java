package gameinfo;

import gameinfo.tile.Tile;

import java.util.List;
import java.util.Optional;

public interface GIGameInfo {

  void registerBy(String id);

  void deregisterBy(String id);

  void start();

  Optional<Boolean> isValidPlayerBy(String id);

  Optional<String> getNextPlayerId();

  // just for test TODO: REMOVE
  Optional<List<String>> getAllPlayerIds();

  Optional<Boolean> play(List<Tile> combination, String id);

  Optional<Boolean> play(List<Tile> tilesFromHand, List<Tile> tilesFromBoard,
      List<List<Tile>> newCombinations, String id);

  Optional<List<Tile>> drawBy(String id);

  Optional<List<Tile>> getAllTilesBy(String id);

  Optional<Integer> getPointsBy(String id);

  Optional<Integer> getNumberOfPlayers();

  Optional<Boolean> finishedTurnBy(String id);
}
