package gameinfo;

import gameinfo.util.GIPoints;
import gameinfo.util.GITile;
import gameinfo.util.GITuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.logging.Logger;

class GameInfoImpl extends Thread implements GIGameInfo {

  private static Logger log = Logger.getLogger(GameInfoImpl.class.getName());

  private static int MINIMUM_POINTS_ON_FIRST_MOVE = 30;
  private static int NUMBER_OF_TILES_IN_STACK = 14;

  private Board board;
  private PointsCalculator calculator;
  private GameFlow gameFlow;
  private CombRules combRules;

  private boolean hasRegisteredPlayers;
  private boolean canModifyRegisteredPlayers;

  GameInfoImpl() {
    board = new Board();
    calculator = new PointsCalculator();
    gameFlow = new GameFlow(calculator);
    combRules = new CombRules(calculator);

    hasRegisteredPlayers = false;
    canModifyRegisteredPlayers = true;
    start();
  }

  @Override
  public void registerBy(Integer id) {
    if (canModifyRegisteredPlayers) {
      gameFlow.registerBy(id);
      hasRegisteredPlayers = true;
    } else {
      log.info(
              "Registration of Player "
                      + id
                      + " has failed. No more players can be registered in the model.");
    }
  }

  @Override
  public void deregisterBy(Integer id) {
    gameFlow.deregisterBy(id);

    if (gameFlow.getPlayers().isEmpty()) {
      hasRegisteredPlayers = false;
    }
  }

  @Override
  public void startGame() {
    gameFlow.startGame();
    canModifyRegisteredPlayers = false;
    log.info("Game started.");
  }

  @Override
  public Optional<GITuple<Integer, Boolean>> isValidPlayerBy(Integer id) {
    Boolean isValidPlayer = gameFlow.isValidPlayerBy(id);
    GITuple<Integer, Boolean> returnValue = new GITuple<>(id, isValidPlayer);
    return Optional.of(returnValue);
  }

  @Override
  public Integer getCurrentPlayerId() {
    return gameFlow.getCurrentPlayerId();
  }

  @Override
  public Optional<Integer> getNextPlayerId() {
    if (hasRegisteredPlayers) {
      return Optional.of(gameFlow.getNextPlayerID());
    } else {
      return Optional.empty();
    }
  }

  // TODO !!! REMOVE AFTER TESTS !!!
  @Override
  public List<Integer> getAllPlayerIds() {
    return new ArrayList<>(gameFlow.getPlayers().keySet());
  }

  @Override
  public Optional<GITuple<Integer, List<GITile>>> drawBy(Integer id) {
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (!optionalPlayer.isPresent()) {
      return Optional.empty();
    }

    if (!gameFlow.isValidPlayerBy(id)) {
      // it is not the players turn.
      return Optional.of(new GITuple<>(id, new ArrayList<>()));
    }

    GITuple<Integer, List<GITile>> returnValue;

    if (gameFlow.isDistributing()) {
      gameFlow.addDistribution();
      gameFlow.nextPlayersTurn();
      returnValue = new GITuple<>(id, getStackFor(id));
      return Optional.of(returnValue);
    } else {
      GITile tile = getTileFor(id);
      List<GITile> tiles = new ArrayList<>();
      tiles.add(tile);
      gameFlow.nextPlayersTurn();
      returnValue = new GITuple<>(id, tiles);
      return Optional.of(returnValue);
    }
  }

  // testing purpose only
  // either one or fourteen tiles !!
  // TODO REMOVE TEST PURPPOSE ONLY
  @Override
  public Optional<GITuple<Integer, List<GITile>>> drawBy(Integer id, List<GITile> customTiles) {
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (!optionalPlayer.isPresent()) {
      return Optional.empty();
    }

    if (!gameFlow.isValidPlayerBy(id)) {
      // it is not the players turn.
      return Optional.of(new GITuple<>(id, new ArrayList<>()));
    }

    GITuple<Integer, List<GITile>> returnValue;

    if (gameFlow.isDistributing()) {
      gameFlow.addDistribution();
      gameFlow.nextPlayersTurn();
      returnValue = new GITuple<>(id, getStackFor(id, customTiles));
      return Optional.of(returnValue);
    } else {
      GITile tile = getTileFor(id, customTiles.get(0));
      List<GITile> tiles = new ArrayList<>();
      tiles.add(tile);
      gameFlow.nextPlayersTurn();
      returnValue = new GITuple<>(id, tiles);
      return Optional.of(returnValue);
    }
  }

  private List<GITile> getStackFor(Integer id, List<GITile> customTiles) {
    // .get() is allowed here because it is always called after isPresent check !!!
    Player player = gameFlow.getPlayerBy(id).get();
    List<GITile> stack = board.getStackFromBag(NUMBER_OF_TILES_IN_STACK, customTiles);
    player.put(stack);
    return stack;
  }

  private GITile getTileFor(Integer id, GITile customTile) {
    // .get() is allowed here because it is always called after isPresent check !!!
    Player player = gameFlow.getPlayerBy(id).get();

    if (board.getTileFromBag(customTile).isPresent()) {
      GITile tile = board.getTileFromBag(customTile).get();
      player.put(tile);
      return tile;
    } else {
      throw new IllegalArgumentException("Tile is not in stack anymore.");
    }
  }

  @Override
  public Optional<GITuple<Integer, List<GITile>>> getAllTilesBy(Integer id) {
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

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
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      Player player = optionalPlayer.get();

      if (!gameFlow.isValidPlayerBy(id)) {
        // it is not the players turn.
        return Optional.of(new GITuple<>(id, false));
      }

      if (player.isFirstMove() && combRules.isValid(combinations, MINIMUM_POINTS_ON_FIRST_MOVE)) {
        putComboOnBoard(combinations, player);
        player.madeMove();
        return Optional.of(new GITuple<>(id, true));
      } else if (!player.isFirstMove() && combRules.isValid(combinations)) {
        putComboOnBoard(combinations, player);
        player.madeMove();
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
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {

      Player player = optionalPlayer.get();

      if (!gameFlow.isValidPlayerBy(id) || player.isFirstMove()) {
        // it is not the players turn or it is his first move.
        return Optional.of(new GITuple<>(id, false));
      }

      boolean allValid = combRules.isValid(newCombinations);

      if (allValid) {
        player.remove(tilesFromHand);
        player.madeMove();
        board.remove(tilesFromBoard);
        newCombinations.forEach(combination -> board.addCombo(combination));
        return Optional.of(new GITuple<>(id, true));
      } else {
        return Optional.of(new GITuple<>(id, false));
      }
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<GITuple<Integer, Boolean>> manipulateBoardWith(List<GITile> tilesFromHand, List<List<GITile>> oldCombinations, List<List<GITile>> newCombinations, Integer id) {
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (!optionalPlayer.isPresent()) {
      return Optional.empty();
    }

    Player player = optionalPlayer.get();

    if (!gameFlow.isValidPlayerBy(id) || player.isFirstMove()) {
      // it is not the players turn or it is his first move.
      return Optional.of(new GITuple<>(id, false));
    }

    boolean allValid = combRules.isValid(newCombinations);

    if (allValid) {
      player.madeMove();
      player.remove(tilesFromHand);
      board.removeMultiple(oldCombinations);
      newCombinations.forEach(combination -> board.addCombo(combination));
      return Optional.of(new GITuple<>(id, true));
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<GITuple<Integer, GIPoints>> calculatePointsBy(Integer id) {

    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (!optionalPlayer.isPresent()) {
      return Optional.empty();
    }

    GITuple<Integer, GIPoints> returnValue =
            new GITuple<>(id, optionalPlayer.get().calculatePointsOfHand());
    return Optional.of(returnValue);
  }

  @Override
  public Optional<Integer> getNumberOfPlayers() {
    return Optional.of(gameFlow.getNumberOfPlayers());
  }

  @Override
  public Optional<GITuple<Integer, List<List<GITile>>>> finishedTurnBy(Integer id) {
    if (!gameFlow.isPlayerExistingBy(id)) {
      return Optional.empty();
    }

    if (gameFlow.isValidPlayerBy(id) && gameFlow.hasMadeMoveBy(id)) {
      gameFlow.nextPlayersTurn();
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
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      return Optional.of(new GITuple<>(id, optionalPlayer.get().isFirstMove()));
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<List<GITuple<Integer, GIPoints>>> calculatePointsForRegisteredPlayers() {
    return gameFlow.getPlayerPoints();
  }

  private List<GITile> getStackFor(Integer id) {
    // .get() is allowed here because it is always called after isPresent check !!!
    Player player = gameFlow.getPlayerBy(id).get();

    List<GITile> stack = board.drawRandomStackWith(NUMBER_OF_TILES_IN_STACK);
    player.put(stack);
    return stack;
  }

  private GITile getTileFor(Integer id) {
    // .get() is allowed here because it is always called after isPresent check !!!
    Player player = gameFlow.getPlayerBy(id).get();

    GITile tile = board.drawRandomTile();
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
}
