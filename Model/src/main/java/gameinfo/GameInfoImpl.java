package gameinfo;

import tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class GameInfoImpl implements GIGameInfo {

  private static int MINIMUM_POINTS_ON_FIRST_MOVE = 30;
  private static int NUMBER_OF_TILES_IN_STACK = 14;

  private Board board;
  private Rules rules;

  GameInfoImpl() {
    board = new Board();
    rules = new Rules();
  }

  @Override
  public void registerBy(String id) {
    rules.registerBy(id);
  }

  @Override
  public void deregisterBy(String id) {
    rules.deregisterPlayerBy(id);
  }

  @Override
  public String getNextPlayerId() {
    return rules.getNextPlayerID();
  }

  // TODO !!! REMOVE AFTER TESTS !!!
  @Override
  public List<String> getAllPlayerIds() {
    List<String> ids = new ArrayList<>();

    for (Player player : rules.getAllPlayers()) {
      ids.add(player.getId());
    }

    return ids;
  }

  @Override
  public Optional<List<Tile>> getAllTilesBy(String id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      Player player = optionalPlayer.get();
      return Optional.of(player.getTilesOnHand());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<List<Tile>> getStackFor(String id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      Player player = optionalPlayer.get();
      List<Tile> stack = board.getStackFromBag(NUMBER_OF_TILES_IN_STACK);
      player.put(stack);
      return Optional.of(stack);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Tile> getTileFor(String id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      Tile tile = board.getTileFromBag();
      Player player = optionalPlayer.get();
      player.put(tile);
      return Optional.of(tile);
    }

    return Optional.empty();
  }

  @Override
  public boolean play(List<Tile> combination, String id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      Player player = optionalPlayer.get();
      if (player.isFirstMove() && rules.isValid(combination, MINIMUM_POINTS_ON_FIRST_MOVE)) {
        putComboOnBoard(combination, player);
        return true;
      } else if (!player.isFirstMove() && rules.isValid(combination)) {
        putComboOnBoard(combination, player);
        return true;
      } else {
        // not a valid combination.
        return false;
      }
    } else {
      // TODO Handle better !
      return false;
    }
  }

  // question which combination has to have 30 points if its first move.
  @Override
  public boolean play(
          List<Tile> tilesFromHand,
          List<Tile> tilesFromBoard,
          List<List<Tile>> newCombinations,
          String id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      boolean allValid = newCombinations.stream().allMatch(this::isValid);

      Player player = optionalPlayer.get();

      if (allValid) {
        player.remove(tilesFromHand);
        // board.remove(tilesFromBoard);
        newCombinations.forEach(combination -> board.addCombo(combination));
        return true;
      } else {
        return false;
      }
    } else {
      // TODO Handle better !
      return false;
    }
  }

  @Override
  public int getPointsForMove(List<Tile> combination) {
    return rules.getPointsFor(combination);
  }

  private void putComboOnBoard(List<Tile> combination, Player player) {
    board.addCombo(combination);
    player.remove(combination);
  }

  private boolean isValid(List<Tile> combination) {
    return rules.isValid(combination);
  }
}
