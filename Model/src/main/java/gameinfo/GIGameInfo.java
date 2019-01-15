package gameinfo;

import gameinfo.tile.Tile;

import java.util.List;
import java.util.Optional;

public interface GIGameInfo {

  void registerBy(Integer id);

  void deregisterBy(Integer id);

  void start();

  Optional<Boolean> isValidPlayerBy(Integer id);

  Optional<Integer> getNextPlayerId();

  // just for test TODO: REMOVE
  List<Integer> getAllPlayerIds();

  Optional<Boolean> play(List<Tile> combination, Integer id);

  Optional<Boolean> play(List<Tile> tilesFromHand, List<Tile> tilesFromBoard,
                         List<List<Tile>> newCombinations, Integer id);

  Optional<List<Tile>> drawBy(Integer id);

  Optional<List<Tile>> getAllTilesBy(Integer id);

  Optional<Integer> getPointsBy(Integer id);

  Optional<Integer> getNumberOfPlayers();

  Optional<Boolean> finishedTurnBy(Integer id);
}
