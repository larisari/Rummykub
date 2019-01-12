package gameinfo;

import tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class GameInfoImpl implements GIGameInfo {

  private Board board;
  private Rules rules;
  private List<Player> players;

  GameInfoImpl() {
    board = new Board();
    rules = new Rules();
    players = new ArrayList<>();
  }

  @Override
  public void registerBy(String id) {
    players.add(new Player(id));
  }

  @Override
  public void deregisterBy(String id) {
    Player player = getPlayerBy(id);

    if (player != null) {
      players.remove(player);
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

    for (Player player : players) {
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
    Player player = getPlayerBy(id);

    if (player != null) {
      List<Tile> stack = board.getStackFromBag();
      player.put(stack);
      return Optional.of(stack);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Tile> getTileFor(String id) {
    Player player = getPlayerBy(id);

    if (player != null) {
      Tile tile = board.getTileFromBag();
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
  public boolean isValidMove(List<Tile> tilesFromHand, List<Tile> tilesFromBoard, List<List<Tile>> newCombination) {
    // TODO Implement
    return false;
  }

  @Override
  public int getPointsForMove(List<Tile> combination) {
    return rules.getPointsFor(combination);
  }

  private Player getPlayerBy(String id) {
    for (Player player : players) {
      if (player.getId().equals(id)) {
        return player;
      }
    }

    return null;
  }
}
