package gameinfo;

import gameinfo.util.GIPoints;
import gameinfo.util.GITuple;
import java.util.*;
/**
 * This class defines the game's general rythm. It explains the player's turn sequence, keeps track
 * of all players as well as the game's progress and current state.
 */
class GameFlow {
  private Map<Integer, Player> players;
  private GameState state;
  private int distributionCounter;
  private List<Player> playerSequence;
  private Integer currentPlayerIndex;
  private PointsCalculator calculator;

  /**
   * The one and only constructor of an GameFlow object only gets a PointsCalculator object as
   * parameter, whereas all other attributes are not set manually by creating this object.
   *
   * @param calculator to calculate possible points.
   */
  GameFlow(PointsCalculator calculator) {
    this.players = new HashMap<>();
    this.state = GameState.distributing;
    this.distributionCounter = 0;
    this.calculator = calculator;
    this.playerSequence = new ArrayList<>();
    this.currentPlayerIndex = 0;
  }

  Integer getStartingPlayerId() {
    return playerSequence.get(currentPlayerIndex).getId();
  }

  /**
   * Method for signing in players according to their age. Each player is assigned a unique ID,
   * which is given as parameter to this method. On the basis of the ID, the player is put into a
   * HashMap with his ID as key-value.
   *
   * @param id of the signed in player.
   */
  void registerBy(Integer id) {
    players.put(id, new Player(id, calculator));
    playerSequence.add(players.get(id));
  }

  /**
   * Each time a player signs out, this method has to be called with the player's unique ID, to
   * remove this player from the game.
   *
   * @param id of the player that wants to sign out of the game.
   */
  void deregisterBy(Integer id) {
    players.remove(id);
    playerSequence.remove(players.get(id));
  }

  /**
   * A simple method to check, whether a player exists or not.
   *
   * @param id of the player that is being checked to exist.
   * @return true, if player with given id exists and false otherwise
   */
  boolean isPlayerExistingBy(Integer id) {
    return getPlayerBy(id).isPresent();
  }

  /**
   * This method causes the game's state to change to "distributing". In this state, each player can
   * draw stacks from the bag.
   */
  void startGame() {
    Collections.sort(playerSequence);
    this.state = GameState.distributing;
  }

  /**
   * Simple method to check whether a player with given id is valid, in other words, whether the
   * player with given id has the correct id.
   *
   * @param id of the player that is being checked for validation.
   * @return true, if player is valid, and false otherwise.
   */
  boolean isValidPlayerBy(Integer id) {
    return players.get(id).getId().equals(id);
  }

  /**
   * This method returns all players in the game.
   *
   * @return all signed in players.
   */
  Map<Integer, Player> getPlayers() {
    return this.players;
  }

  /**
   * Accessing a Player object with given id is possible with this method.
   *
   * @param id of the player.
   * @return an Optional Player object, in case the given id matches a key-value in the HashMap with
   *     a valid value and an empty Optional if no player is found.
   */
  Optional<Player> getPlayerBy(Integer id) {
    if (players.containsKey(id)) {
      return Optional.of(players.get(id));
    } else {
      return Optional.empty();
    }
  }

  /**
   * This method is used to
   *
   * @return the current player's id.
   */
  Integer getCurrentPlayerId() {
    return playerSequence.get(currentPlayerIndex).getId();
  }

  /**
   * This method is used to
   *
   * @return the next player's id.
   */
  Integer getNextPlayerID() {
    if (currentPlayerIndex < playerSequence.size() - 1) {
      return playerSequence.get(currentPlayerIndex + 1).getId();
    } else {
      return playerSequence.get(0).getId();
    }
  }

  /** This method causes the next player's turn. */
  void nextPlayersTurn() {
    if (currentPlayerIndex < playerSequence.size() - 1) {
      currentPlayerIndex++;
    } else {
      currentPlayerIndex = 0;
    }
    Integer currentPlayerId = playerSequence.get(currentPlayerIndex).getId();
    players.get(currentPlayerId).resetMadeMove();
  }

  /**
   * Simple method to check whether the current state of the game is "distributing", in other words,
   * whether stacks are being distributed.
   *
   * @return true, if game is in distributing-state, and false otherwise.
   */
  boolean isDistributing() {
    return state.equals(GameState.distributing);
  }

  /**
   * This method is used to
   *
   * @return the number of players in the game.
   */
  int getNumberOfPlayers() {
    return this.players.size();
  }

  /**
   * Causing the distributionCounter of the class to increase. Reaching the number of players, the
   * state will change from "distributing" to "running".
   */
  void addDistribution() {
    this.distributionCounter++;
    if (distributionCounter == getNumberOfPlayers()) {
      this.state = GameState.running;
    }
  }

  /**
   * Accessing the players' points is possible with this method.
   *
   * @return each players hands and their points.
   */
  Optional<List<GITuple<Integer, GIPoints>>> getPlayerPoints() {
    if (!players.isEmpty()) {
      List<GITuple<Integer, GIPoints>> allPlayersHands = new ArrayList<>();
      for (Map.Entry<Integer, Player> player : players.entrySet()) {
        GITuple<Integer, GIPoints> playersHand =
            new GITuple<>(player.getValue().getId(), player.getValue().calculatePointsOfHand());
        allPlayersHands.add(playersHand);
      }
      return Optional.of(allPlayersHands);
    } else {
      return Optional.empty();
    }
  }

  /**
   * This method let's you know, whether a player has made a move.
   *
   * @param id of the player that possibly made a move.
   * @return true, if player absolved a move successfully, and false otherwise.
   */
  boolean hasMadeMoveBy(Integer id) {
    return players.get(id).hasMadeMove();
  }
}
