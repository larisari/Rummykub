package gameinfo;

import gameinfo.util.GIPoints;
import gameinfo.util.GITile;
import gameinfo.util.GITuple;

import java.util.List;
import java.util.Optional;

public interface GIGameInfo {

  void registerBy(Integer id);

  void deregisterBy(Integer id);

  void start();

  Optional<Integer> getNumberOfPlayers();

  List<Integer> getAllPlayerIds();

  Optional<Integer> getNextPlayerId();

  Optional<GITuple<Integer, Boolean>> isValidPlayerBy(Integer id);

  Optional<GITuple<Integer, List<GITile>>> drawBy(Integer id);

  Optional<GITuple<Integer, Boolean>> play(List<List<GITile>> combinations, Integer id);

  Optional<GITuple<Integer, Boolean>> play(
      List<GITile> tilesFromHand,
      List<GITile> tilesFromBoard,
      List<List<GITile>> newCombinations,
      Integer id);

  Optional<GITuple<Integer, List<GITile>>> getAllTilesBy(Integer id);

  Optional<GITuple<Integer, GIPoints>> getPointsBy(Integer id);

  Optional<GITuple<Integer, Boolean>> finishedTurnBy(Integer id);
}
