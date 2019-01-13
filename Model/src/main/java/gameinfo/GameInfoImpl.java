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
  private boolean canModifyRegisteredPlayers;

  GameInfoImpl() {
    board = new Board();
    rules = new Rules();
    hasRegisteredPlayers = false;
    canModifyRegisteredPlayers = true;
  }

  @Override
  public void registerBy(String id) {
    if (canModifyRegisteredPlayers) {
      rules.registerBy(id);
      hasRegisteredPlayers = true;
    }
  }

  @Override
  public void deregisterBy(String id) {

    if (canModifyRegisteredPlayers) {
      rules.deregisterPlayerBy(id);

      if (rules.getAllPlayers().isEmpty()) {
        hasRegisteredPlayers = false;
      }
    }
  }

  @Override
  public void start() {
    rules.startGame();
    canModifyRegisteredPlayers = false;
  }

  @Override
  public Optional<Boolean> isValidPlayerBy(String id) {
    return Optional.of(this.rules.isValidPlayer(id));
  }

  @Override
  public Optional<String> getNextPlayerId() {
    if (hasRegisteredPlayers) {
      return Optional.of(rules.getNextPlayerID());
    } else {
      return Optional.empty();
    }
  }

  // TODO !!! REMOVE AFTER TESTS !!!
  @Override
  public Optional<List<String>> getAllPlayerIds() {
    List<String> ids = new ArrayList<>();

    for (Player player : rules.getAllPlayers()) {
      ids.add(player.getId());
    }

    if (ids.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(ids);
    }
  }

  @Override
  public Optional<List<Tile>> drawBy(String id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (!optionalPlayer.isPresent()) {
      return Optional.empty();
    }

    if (rules.isDistributing()) {
      rules.firstDraw();
      return Optional.of(getStackFor(id));
    } else {
      Tile tile = getTileFor(id);
      List<Tile> tiles = new ArrayList<>();
      tiles.add(tile);
      return Optional.of(tiles);
    }

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
  public Optional<Boolean> play(List<Tile> combination, String id) {
    Optional<Player> optionalPlayer = rules.getPlayerBy(id);

    if (optionalPlayer.isPresent()) {
      Player player = optionalPlayer.get();
      if (player.isFirstMove() && rules.isValid(combination, MINIMUM_POINTS_ON_FIRST_MOVE)) {
        putComboOnBoard(combination, player);
        return Optional.of(true);
      } else if (!player.isFirstMove() && rules.isValid(combination)) {
        putComboOnBoard(combination, player);
        return Optional.of(true);
      } else {
        // not a valid combination.
        return Optional.of(false);
      }
    } else {
      // TODO Handle better !
      return Optional.empty();
    }
  }

  // question which combination has to have 30 points if its first move.
  @Override
  public Optional<Boolean> play(
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
        return Optional.of(true);
      } else {
        return Optional.of(false);
      }
    } else {
      // TODO Handle better !
      // if no valid plauyer with id id
      return Optional.empty();
    }
  }

  @Override
  public Optional<Integer> getPointsForMove(List<Tile> combination) {
    return Optional.of(rules.getPointsFor(combination));
  }

  private List<Tile> getStackFor(String id) {
    Player player = rules.getPlayerBy(id).get();

    List<Tile> stack = board.getStackFromBag(NUMBER_OF_TILES_IN_STACK);
    player.put(stack);
    return stack;
  }

  private Tile getTileFor(String id) {
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

  @Override
  public int getNumberOfPlayers() {
    return rules.getNumberOfPlayers();
  }

  @Override
  public void next() {
    this.rules.nextPlayer();
  }



}
