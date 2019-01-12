package gameinfo;

import tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class GameInfoImpl implements GIGameInfo {

  private Board board;
  private Rules rules;
  private GameFlow gameFlow;
  private final int STARTING_TILES = 14;

  GameInfoImpl() {
    board = new Board();
    rules = new Rules();
    gameFlow = new GameFlow();
  }

  @Override
  public void registerBy(String id) {
    gameFlow.registerPlayerBy(id);
  }

  @Override
  public void deregisterBy(String id) {
    Player player = getPlayerBy(id);

    if (player != null) {
      gameFlow.deregisterPlayerBy(id);
    }
  }

  @Override
  public String getNextPlayerId() {
    return rules.getNextPlayerID();
  }

  // TODO !!! REMOVE AFTER TESTS !!!
  @Override
  public List<String> getAllPlayerIds() {
    List<String> ids = new ArrayList<>();

    for (Player player : gameFlow.getPlayers()) {
      ids.add(player.getId());
    }

    return ids;
  }

  @Override
  public Optional<List<Tile>> getAllTilesBy(String id) {
    Player player = getPlayerBy(id);

    if (player != null) {
      return Optional.of(player.getTilesOnHand());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<List<Tile>> getStackFor(String id) {
    List<Tile> stack = board.getStackFromBag(STARTING_TILES);
    Player player = getPlayerBy(id);

    if (player != null) {
      player.put(stack);
      return Optional.of(stack);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Tile> getTileFor(String id) {
    Tile tile = board.getTileFromBag();
    Player player = getPlayerBy(id);

    if (player != null) {
      player.put(tile);
      return Optional.of(tile);
    }

    return Optional.empty();
  }

  @Override
  public boolean isValidMove(List<Tile> combination) {
    // TODO !!! if its first move use other method of rules.CombRules with minimum Points !!!
    return rules.isValid(combination);
  }

  @Override
  public int getPointsForMove(List<Tile> combination) {
    return rules.getPointsFor(combination);
  }

  private Player getPlayerBy(String id) {
    for (Player player : gameFlow.getPlayers()) {
      if (player.getId().equals(id)) {
        return player;
      }
    }

    return null;
  }
}
