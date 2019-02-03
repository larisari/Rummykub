package gameinfo;

import gameinfo.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
/**
 * This Class shows an exemplary implementation of the GIGameInfo interface. It serves as a central
 * management class for the model.
 */
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

  /**
   * Use this method whenever you wish to sign in a player to the game.
   *
   * @param id of the player to be signed in.
   */
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

  /**
   * Use this method to assign a player with given id its age.
   *
   * @param id of the player whose age is being set.
   * @param age of the player to be set.
   */
  @Override
  public void setAgeFor(Integer id, int age) {
    if (gameFlow.getPlayerBy(id).isPresent()) {
      gameFlow.getPlayerBy(id).get().setAge(age);
    }
  }

  /**
   * Use this method, whenever you wish to sign out a player from the game.
   *
   * @param id of the player to be signed out.
   */
  @Override
  public void deregisterBy(Integer id) {
    gameFlow.deregisterBy(id);

    if (gameFlow.getPlayers().isEmpty()) {
      hasRegisteredPlayers = false;
    }
  }

  /**
   * Use this method to start the game. Keep in mind, that from now on no other player can join the
   * game and sign in.
   */
  @Override
  public void startGame() {
    gameFlow.startGame();
    canModifyRegisteredPlayers = false;
    log.info("Game started.");
  }

  /**
   * Use this method to get the number of Players registered in the Model.
   *
   * @return number of registered Players.
   */
  @Override
  public Optional<Integer> getNumberOfPlayers() {
    return Optional.of(gameFlow.getNumberOfPlayers());
  }

  /**
   * Use this method to get all the ids registered in the Model.
   *
   * @return all Player ids.
   */
  @Override
  public List<Integer> getAllPlayerIds() {
    return new ArrayList<>(gameFlow.getPlayers().keySet());
  }

  /**
   * Use this method to identify the starting player's id.
   *
   * @return id of the first player.
   */
  @Override
  public Integer getStartingPlayerId() {
    return gameFlow.getStartingPlayerId();
  }

  /**
   * Use this method to obtain the id of the player whose turn it is.
   *
   * @return the current player's id.
   */
  @Override
  public Integer getCurrentPlayerId() {
    return gameFlow.getCurrentPlayerId();
  }

  /**
   * Use this method to get the Player id of the next valid Player.
   *
   * @return the id of the next valid Player as an Optional.
   */
  @Override
  public Optional<Integer> getNextPlayerId() {
    if (hasRegisteredPlayers) {
      return Optional.of(gameFlow.getNextPlayerID());
    } else {
      return Optional.empty();
    }
  }

  /**
   * Use this method to check whether the passed id matches the current player's id.
   *
   * @param id to be checked.
   * @return a GITuple<id,true> if the player is valid, a GITuple<id,false> if the id does not match
   *     and an Optional.empty otherwise.
   */
  @Override
  public Optional<GITuple<Integer, Boolean>> isValidPlayerBy(Integer id) {
    Boolean isValidPlayer = gameFlow.isValidPlayerBy(id);
    GITuple<Integer, Boolean> returnValue = new GITuple<>(id, isValidPlayer);
    return Optional.of(returnValue);
  }

  /**
   * Use this method is check if it's the first turn of a player.
   *
   * @param id of the player to be checked for first turn.
   * @return the parameter id if it's the first turn of the player. If the player's id is not
   *     registered in the model an Optional.empty() is returned.
   */
  @Override
  public Optional<GITuple<Integer, Boolean>> isFirstTurnBy(Integer id) {
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      return Optional.of(new GITuple<>(id, optionalPlayer.get().isFirstMove()));
    } else {
      return Optional.empty();
    }
  }

  /**
   * Use this method to draw a stack (containing 14 Tiles) for a Player id if it's the players first
   * draw or else a single tile. It also automatically ends and calculates the next player's turn.
   *
   * @param id for which the Stack/Tile is drawn for.
   * @return the id for which the stack/tile was drawn fro and the tiles as a list. If an
   *     Optional.empty() is returned the id is not registered in the model.
   */
  @Override
  public Optional<GITuple<Integer, List<GITile>>> drawBy(Integer id) {
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (!optionalPlayer.isPresent()) {
      return Optional.empty();
    }

    if (!id.equals(getCurrentPlayerId())) {
      // it is not the players turn.
      return Optional.of(new GITuple<>(id, new ArrayList<>()));
    }

    if (bagIsEmpty()) {
      log.info("Bag is empty, a new bag is being generated.");
      board.generateNewBag();
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

  /**
   * Use this method to validate a combination for a player by its id. If the combination is valid
   * it is saved in the model and put on the board. If it is not valid nothing happens in the model
   * but false is returned. More than just one combination can be played with this method. If one of
   * these combinations is invalid it will change nothing in the model and return false.
   *
   * @param combinations to be checked for validity.
   * @param id of the player to play the combination.
   * @return the id by which the combinations were played, and whether the combinations were valid
   *     or not. If the player id is not registered in the model an Optional .empty() is returned.
   */
  @Override
  public Optional<GITuple<Integer, Boolean>> play(List<List<GITile>> combinations, Integer id) {
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      Player player = optionalPlayer.get();

      if (!id.equals(getCurrentPlayerId())) {
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

  /**
   * Use this method to validate a combination for a player by its id, where tiles from the players
   * hand are combined with tiles from the board. Only one new combination can be played with this
   * method. Also the whole Board has to be checked for validity, because other combinations on the
   * board can be manipulated.
   *
   * @param tilesFromHand the tiles that where played from the players hand.
   * @param tilesFromBoard the combination to which the tilesFromHand were added
   * @param newCombinations all the combinations that are currently on the board.
   * @param id by which the combination is played.
   * @return the id by which the combination was played, and whether the combination was valid or
   *     not. If the player id is not registered in the model an Optional.empty() is returned.
   */
  @Override
  public Optional<GITuple<Integer, Boolean>> play(
      List<GITile> tilesFromHand,
      List<GITile> tilesFromBoard,
      List<List<GITile>> newCombinations,
      Integer id) {
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {

      Player player = optionalPlayer.get();

      if (!id.equals(getCurrentPlayerId()) || player.isFirstMove()) {
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

  /**
   * Use this method to validate a combination for a player by its id, where tiles from the players
   * hand and an old combination from the board are combined to a new combination.
   *
   * @param tilesFromHand tiles that were selected from the hand
   * @param oldCombinations tiles from the old combination (in order).
   * @param newCombinations tiles from the new combination (in order).
   * @param id the id by which the move was made
   * @return the id by which the combination was played, and whether the new combination was valid
   *     or not. If the player id is not registered in the model an Optional.empty() is returned.
   */
  @Override
  public Optional<GITuple<Integer, Boolean>> manipulateBoardWith(
      List<GITile> tilesFromHand,
      List<List<GITile>> oldCombinations,
      List<List<GITile>> newCombinations,
      Integer id) {
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (!optionalPlayer.isPresent()) {
      return Optional.empty();
    }

    Player player = optionalPlayer.get();

    if (!id.equals(getCurrentPlayerId()) || player.isFirstMove()) {
      // it is not the players turn or it is his first move.
      return Optional.of(new GITuple<>(id, false));
    }

    final GITile JOKER = new GITile(GINumber.JOKER, GIColor.JOKER);

    boolean allValid = combRules.isValid(newCombinations);

    if (allValid) {
      log.info("all valid.");

      long jokersInOld =
          oldCombinations
              .stream()
              .flatMap(List::stream)
              .filter(tile -> tile.isEquals(JOKER))
              .count();

      long jokersInNew =
          newCombinations
              .stream()
              .flatMap(List::stream)
              .filter(tile -> tile.isEquals(JOKER))
              .count();

      if (jokersInNew == jokersInOld - 1) {
        // joker got deleted from board
        player.put(JOKER);
      }

      player.madeMove();
      player.remove(tilesFromHand);
      board.removeMultiple(oldCombinations);
      newCombinations.forEach(combination -> board.addCombo(combination));
      return Optional.of(new GITuple<>(id, true));
    } else {
      return Optional.of(new GITuple<>(id, false));
    }
  }

  /**
   * Use this method to signal the end of a players turn by its id.
   *
   * @param id the id for which the end of he turn is signaled.
   * @return the id by for which the turn was ended and the whole board. If the player id is not
   *     registered in the model an Optional.empty() is returned.
   */
  @Override
  public Optional<Integer> finishedTurnBy(Integer id) {
    if (!gameFlow.isPlayerExistingBy(id)) {
      return Optional.empty();
    }
    if (gameFlow.isValidPlayerBy(id) && gameFlow.hasMadeMoveBy(id)) {
      gameFlow.nextPlayersTurn();
      return Optional.of(id);
    } else {
      return Optional.empty();
    }
  }

  /**
   * Use this method to get all the tiles on the hand by an id.
   *
   * @param id for which the hand is requested.
   * @return the id by which the hand was requested and all the tiles of the id. If the player id is
   *     not registered in the model an Optional.empty() is returned.
   */
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

  /**
   * Use this method to calculate the points for a player by its id. The winner has 0 points, thus
   * all other points are negative integer.
   *
   * @param id of the player whose points are wished.
   * @return the id for which the points of the hand were requested and the points as negative
   *     GIPoints. If the player id is not registered in the model an Optional.empty() is returned.
   */
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

  /**
   * Use this method to calculate the points of all players' hands.
   *
   * @return a list of tuples which consist of an id and corresponding points.
   */
  @Override
  public Optional<List<GITuple<Integer, GIPoints>>> calculatePointsForRegisteredPlayers() {
    return gameFlow.getPlayerPoints();
  }

  /**
   * Use this method to obtain the up to date board.
   *
   * @return current board.
   */
  @Override
  public List<List<GITile>> getCurrentBoard() {
    return board.getActiveCombos();
  }

  // vvv Auxiliary methods vvv

  /**
   * Auxiliary method to draw a random tile for a player.
   *
   * @param id of the player who gets a tile.
   * @return the randomly pulled GITile object.
   */
  private GITile getTileFor(Integer id) {
    // .get() is allowed here because it is always called after isPresent check !!!
    Player player = gameFlow.getPlayerBy(id).get();

    GITile tile = board.drawRandomTile();
    player.put(tile);

    return tile;
  }

  /**
   * Auxiliary method to distribute a random stack to the player whose given id matches.
   *
   * @param id of the player to get a stack.
   * @return List of tiles to be distributed.
   */
  private List<GITile> getStackFor(Integer id) {
    // .get() is allowed here because it is always called after isPresent check !!!
    Player player = gameFlow.getPlayerBy(id).get();

    List<GITile> stack = board.drawRandomStackWith(NUMBER_OF_TILES_IN_STACK);
    player.put(stack);
    return stack;
  }

  /**
   * Auxiliary method to place one or several valid combinations on the game board.
   *
   * @param combinations to be put on the board.
   * @param player to demand the move.
   */
  private void putComboOnBoard(List<List<GITile>> combinations, Player player) {
    combinations.forEach(
        combination -> {
          board.addCombo(combination);
          player.remove(combination);
        });
  }

  /**
   * Auxiliary method to check whether the game board's bag is empty.
   *
   * @return true, in case the bag is empty and false otherwise.
   */
  private boolean bagIsEmpty() {
    return board.bagIsEmpty();
  }

  // vvv TESTING PURPOSE ONLY vvv

  /**
   * A method for testing purpose only. It distributes a number of determined tiles for the player
   * whose id matches with the passed one.
   *
   * @param id of the player to get a custom tile.
   * @param customTiles GITiles to be distributed to the player.
   * @return GITiles that are distributed to the player.
   */
  @Override
  public Optional<GITuple<Integer, List<GITile>>> drawBy(Integer id, List<GITile> customTiles) {
    Optional<Player> optionalPlayer = gameFlow.getPlayerBy(id);

    if (!optionalPlayer.isPresent()) {
      return Optional.empty();
    }

    if (!id.equals(getCurrentPlayerId())) {
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

  /**
   * A method for testing purpose only. It distributes a determined tile for the player whose id
   * matches with the passed one.
   *
   * @param id of the player to get a custom tile.
   * @param customTile GITile to be distributed to the player.
   * @return GITile that is distributed to the player.
   */
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

  /**
   * A method for testing purposes only. It helps obtaining a custom stack in form of a List of
   * GITiles.
   *
   * @param id of the player to get a stack.
   * @param customTiles to be passed to the player.
   * @return List of tiles for the player.
   */
  private List<GITile> getStackFor(Integer id, List<GITile> customTiles) {
    // .get() is allowed here because it is always called after isPresent check !!!
    Player player = gameFlow.getPlayerBy(id).get();
    List<GITile> stack = board.getStackFromBag(NUMBER_OF_TILES_IN_STACK, customTiles);
    player.put(stack);
    return stack;
  }
}
