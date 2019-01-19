package gameinfo;

import gameinfo.util.GIPoints;
import gameinfo.util.GITile;
import gameinfo.util.GITuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

class GameInfoImpl extends Thread implements GIGameInfo {

  private static Logger log = Logger.getLogger(GameInfoImpl.class.getName());

  private static int MINIMUM_POINTS_ON_FIRST_MOVE = 30;
  private static int NUMBER_OF_TILES_IN_STACK = 14;

  private Board board;
  private Rules rules;

  private boolean hasRegisteredPlayers;
  private boolean canModifyRegisteredPlayers;

  GameInfoImpl() {
    board = new Board();
    rules = new Rules();
    hasRegisteredPlayers = false;
    canModifyRegisteredPlayers = true;
    start();
  }

  @Override
  public void registerBy(Integer id) {
    if (canModifyRegisteredPlayers) {
      rules.registerBy(id);
      hasRegisteredPlayers = true;

      log.info("Registered Player " + id + ".");
    } else {
      log.info("Registering of Player " + id + " has failed.");
    }
  }

  @Override
  public void deregisterBy(Integer id) {
    rules.deregisterPlayerBy(id);

    log.info("Deregistered Player " + id + ".");

    if (rules.getAllPlayers().isEmpty()) {
      hasRegisteredPlayers = false;
    }
  }

  @Override
  public void startGame() {
    rules.startGame();
    canModifyRegisteredPlayers = false;
    log.info("Game started.");
  }

  @Override
  public Optional<GITuple<Integer, Boolean>> isValidPlayerBy(Integer id) {
    Boolean isValidPlayer = rules.isValidPlayerBy(id);
    GITuple<Integer, Boolean> returnValue = new GITuple<>(id, isValidPlayer);
    return Optional.of(returnValue);
  }

  @Override
  public Optional<Integer> getNextPlayerId() {
    if (hasRegisteredPlayers) {
      return Optional.of(rules.getNextPlayerID());
    } else {
      return Optional.empty();
    }
  }

  // TODO !!! REMOVE AFTER TESTS !!!
  @Override
  public List<Integer> getAllPlayerIds() {
    return new ArrayList<>(rules.getAllPlayers().keySet());
  }

  @Override
  public Optional<GITuple<Integer, List<GITile>>> drawBy(Integer id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (!optionalPlayer.isPresent()) {
      return Optional.empty();
    }

    if (!rules.isValidPlayerBy(id)) {
      // it is not the players turn.
      return Optional.of(new GITuple<>(id, new ArrayList<>()));
    }

    GITuple<Integer, List<GITile>> returnValue;

    if (rules.isDistributing()) {
      rules.addDistribution();
      rules.nextPlayersTurn();
      returnValue = new GITuple<>(id, getStackFor(id));
      return Optional.of(returnValue);
    } else {
      GITile tile = getTileFor(id);
      List<GITile> tiles = new ArrayList<>();
      tiles.add(tile);
      rules.nextPlayersTurn();
      returnValue = new GITuple<>(id, tiles);
      return Optional.of(returnValue);
    }
  }

  @Override
  public Optional<GITuple<Integer, List<GITile>>> getAllTilesBy(Integer id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      Player player = optionalPlayer.get();
      GITuple<Integer, List<GITile>> returnValue = new GITuple<>(id, player.getTilesOnHand());
      return Optional.of(returnValue);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<GITuple<Integer, Boolean>> play(List<List<GITile>> combinations, Integer id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      Player player = optionalPlayer.get();

      if (!rules.isValidPlayerBy(id)) {
        // it is not the players turn.
        return Optional.of(new GITuple<>(id, false));
      }

      if (player.isFirstMove() && rules.isValid(combinations, MINIMUM_POINTS_ON_FIRST_MOVE)) {
        putComboOnBoard(combinations, player);
        rules.nextPlayersTurn();
        return Optional.of(new GITuple<>(id, true));
      } else if (!player.isFirstMove() && rules.isValid(combinations)) {
        putComboOnBoard(combinations, player);
        rules.nextPlayersTurn();
        return Optional.of(new GITuple<>(id, true));
      } else {
        // not a valid combination.
        return Optional.of(new GITuple<>(id, false));
      }
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<GITuple<Integer, Boolean>> play(
          List<GITile> tilesFromHand,
          List<GITile> tilesFromBoard,
          List<List<GITile>> newCombinations,
          Integer id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {

      Player player = optionalPlayer.get();

      if (!rules.isValidPlayerBy(id) || player.isFirstMove()) {
        // it is not the players turn or it is his first move.
        return Optional.of(new GITuple<>(id, false));
      }

      boolean allValid = rules.isValid(newCombinations);

      if (allValid) {
        player.remove(tilesFromHand);
        board.remove(tilesFromBoard);
        newCombinations.forEach(combination -> board.addCombo(combination));
        rules.nextPlayersTurn();
        return Optional.of(new GITuple<>(id, true));
      } else {
        return Optional.of(new GITuple<>(id, false));
      }
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<GITuple<Integer, GIPoints>> calculatePointsBy(Integer id) {

    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (!optionalPlayer.isPresent()) {
      return Optional.empty();
    }

    GITuple<Integer, GIPoints> returnValue =
            new GITuple<>(id, optionalPlayer.get().calculatePointsOfHand());
    return Optional.of(returnValue);
  }

  @Override
  public Optional<Integer> getNumberOfPlayers() {
    return Optional.of(rules.getNumberOfPlayers());
  }

  @Override
  public Optional<GITuple<Integer, List<List<GITile>>>> finishedTurnBy(Integer id) {
    if (!rules.isPlayerExistingBy(id)) {
      return Optional.empty();
    }

    if (rules.isValidPlayerBy(id)) {
      rules.nextPlayersTurn();
      return Optional.of(new GITuple<>(id, board.getActiveCombos()));
    } else {
      // it is not the players turn.
      // return Optional.of(new GITuple<>(id, board.getActiveCombos()));
      // TODO !!! HANDLE BETTER !!!
      return Optional.empty();
    }
  }

  @Override
  public Optional<GITuple<Integer, Boolean>> isFirstTurnBy(Integer id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      return Optional.of(new GITuple<>(id, optionalPlayer.get().isFirstMove()));
    } else {
      return Optional.empty();
    }
  }

  private List<GITile> getStackFor(Integer id) {
    // .get() is allowed here because it is always called after isPresent check !!!
    Player player = rules.getPlayerBy(id).get();

    List<GITile> stack = board.getStackFromBag(NUMBER_OF_TILES_IN_STACK);
    player.put(stack);
    return stack;
  }

  private GITile getTileFor(Integer id) {
    // .get() is allowed here because it is always called after isPresent check !!!
    Player player = rules.getPlayerBy(id).get();

    GITile tile = board.getTileFromBag();
    player.put(tile);

    return tile;
  }

  private void putComboOnBoard(List<List<GITile>> combinations, Player player) {
    combinations.forEach(
            combination -> {
              board.addCombo(combination);
              player.remove(combination);
            });
  }

  @Override
  public List<GITuple<Integer,GIPoints>> getPlayerPoints() {
    return rules.getPlayerPoints();
  }
}
