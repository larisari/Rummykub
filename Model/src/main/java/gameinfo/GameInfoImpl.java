package gameinfo;

import gameinfo.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class GameInfoImpl implements GIGameInfo {

  private static int MINIMUM_POINTS_ON_FIRST_MOVE = 30;
  private static int NUMBER_OF_TILES_IN_STACK = 14;

  private Board board;
  private Rules rules;

  private boolean hasRegisteredPlayers;

  GameInfoImpl() {
    board = new Board();
    rules = new Rules();
    hasRegisteredPlayers = false;
  }

  @Override
  public void registerBy(Integer id) {
    rules.registerBy(id);
    hasRegisteredPlayers = true;
  }

  @Override
  public void deregisterBy(Integer id) {
    rules.deregisterPlayerBy(id);

    if (rules.getAllPlayers().isEmpty()) {
      hasRegisteredPlayers = false;
    }
  }

  @Override
  public void start() {
    rules.startGame();
  }

  @Override
  public Optional<Boolean> isValidPlayerBy(Integer id) {
    return Optional.of(this.rules.isValidPlayerBy(id));
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
  public Optional<List<Tile>> drawBy(Integer id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (!optionalPlayer.isPresent()) {
      return Optional.empty();
    }

    if (!rules.isValidPlayerBy(id)) {
      // it is not the players turn.
      return Optional.of(new ArrayList<>());
    }

    if (rules.isDistributing()) {
      rules.addDistribution();
      rules.nextPlayersTurn();
      return Optional.of(getStackFor(id));
    } else {
      Tile tile = getTileFor(id);
      List<Tile> tiles = new ArrayList<>();
      tiles.add(tile);
      rules.nextPlayersTurn();
      return Optional.of(tiles);
    }
  }

  @Override
  public Optional<List<Tile>> getAllTilesBy(Integer id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      Player player = optionalPlayer.get();
      return Optional.of(player.getTilesOnHand());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Boolean> play(List<Tile> combination, Integer id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      Player player = optionalPlayer.get();

      if (!rules.isValidPlayerBy(id)) {
        // it is not the players turn.
        return Optional.of(false);
      }

      if (player.isFirstMove() && rules.isValid(combination, MINIMUM_POINTS_ON_FIRST_MOVE)) {
        putComboOnBoard(combination, player);
        rules.nextPlayersTurn();
        return Optional.of(true);
      } else if (!player.isFirstMove() && rules.isValid(combination)) {
        putComboOnBoard(combination, player);
        rules.nextPlayersTurn();
        return Optional.of(true);
      } else {
        // not a valid combination.
        return Optional.of(false);
      }
    } else {
      return Optional.empty();
    }
  }

  // question which combination has to have 30 points if its first move.
  @Override
  public Optional<Boolean> play(
      List<Tile> tilesFromHand,
      List<Tile> tilesFromBoard,
      List<List<Tile>> newCombinations,
      Integer id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {

      if (!rules.isValidPlayerBy(id)) {
        // it is not the players turn.
        return Optional.of(false);
      }

      boolean allValid = newCombinations.stream().allMatch(this::isValid);

      Player player = optionalPlayer.get();

      if (allValid) {
        player.remove(tilesFromHand);
        board.remove(tilesFromBoard);
        newCombinations.forEach(combination -> board.addCombo(combination));
        rules.nextPlayersTurn();
        return Optional.of(true);
      } else {
        return Optional.of(false);
      }
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Integer> getPointsBy(Integer id) {

    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (!optionalPlayer.isPresent()) {
      return Optional.empty();
    }

    return Optional.of(optionalPlayer.get().getPointsOfHand());
  }

  @Override
  public Optional<Integer> getNumberOfPlayers() {
    return Optional.of(rules.getNumberOfPlayers());
  }

  @Override
  public Optional<Boolean> finishedTurnBy(Integer id) {
    if (!rules.isPlayerExistingBy(id)) {
      return Optional.empty();
    }

    if (rules.isValidPlayerBy(id)) {
      rules.nextPlayersTurn();
      return Optional.of(true);
    } else {
      // it is not the players turn.
      return Optional.of(false);
    }
  }

  private List<Tile> getStackFor(Integer id) {
    Player player = rules.getPlayerBy(id).get();

    List<Tile> stack = board.getStackFromBag(NUMBER_OF_TILES_IN_STACK);
    player.put(stack);
    return stack;
  }

  private Tile getTileFor(Integer id) {
    Player player = rules.getPlayerBy(id).get();

    Tile tile = board.getTileFromBag();
    player.put(tile);

    return tile;
  }

  private void putComboOnBoard(List<Tile> combination, Player player) {
    board.addCombo(combination);
    player.remove(combination);
  }

  private boolean isValid(List<Tile> combination) {
    return rules.isValid(combination);
  }
}
