package gameinfo;

import gameinfo.util.GIPoints;
import gameinfo.util.GITile;
import gameinfo.util.GITuple;

import java.util.List;
import java.util.Optional;

public interface GIGameInfo {

  /**
   * Registers a Player in the Model.
   *
   * @param id for which the Player is registered in the model.
   */
  void registerBy(Integer id);

  /**
   * Deregister a Player in the Model. (Removes the Player from the GameFlow).
   *
   * @param id for which the player is deregistered.
   */
  void deregisterBy(Integer id);

  /** Starts the game. From now on no other Players can be registered. */
  void startGame();

  /**
   * Use this method to get the number of Players registered in the Model.
   *
   * @return number of registered Players.
   */
  Optional<Integer> getNumberOfPlayers();

  /**
   * Use this method to get all the ids registered in the Model.
   *
   * @return all Player ids.
   */
  List<Integer> getAllPlayerIds();

  /**
   * Use this method to get the Player id of the next valid Player.
   *
   * @return the id of the next valid Player as an Optional.
   */
  Optional<Integer> getNextPlayerId();

  /**
   * Use this method to check if the id that is passed is the one of the current player.
   *
   * @param id the player id for which is checked if he/she is the valid player.
   * @return if the Player is Valid. If the id is not registered in the model, then an
   *     Optional.empty() is returned.
   */
  Optional<GITuple<Integer, Boolean>> isValidPlayerBy(Integer id);

  /**
   * Use this method to draw a stack (containing 14 Tiles) for a Player id if its the players first
   * draw or else a single tile. It also automatically ends the players turn and calculates the next
   * ones.
   *
   * @param id for which the Stack/Tile is drawn for.
   * @return the id for which the stack/tile was drawn fro and the tiles as a list. If an
   *     Optional.empty() is returned the id is not registered in the model.
   */
  Optional<GITuple<Integer, List<GITile>>> drawBy(Integer id);

  /**
   * Use this method to validate a combination for a player by its id. If the combination is valid
   * it is saved in the model and put on the board. If it is not valid nothing happens in the model
   * but false is returned. More than just one combination can be played with this method. If one of
   * these combinations is invalid it will change nothing in the model and return false.
   *
   * @param combinations the combination that has to be checked for validity.
   * @param id by which the combinations are played.
   * @return the id by which the combinations were played, and whether the combinations were valid
   *     or not. If the player id is not registered in the model an Optional.empty() is returned.
   */
  Optional<GITuple<Integer, Boolean>> play(List<List<GITile>> combinations, Integer id);

  /**
   * Use this method to validate a combination for a player by its id, where tiles from the players
   * hand are combined with tiles from the board. Only one new combination can be played with this
   * method. Also the whole Board has to be checked for validity, because othe combinations on the
   * board can be manipulated. If the player id is not registered in the model an Optional.empty()
   * is returned.
   *
   * @param tilesFromHand the tiles that where played from the players hand.
   * @param tilesFromHand the combination to which the tilesFromHand were added
   * @param tilesFromBoard all the combinations that are currently on the board.
   * @param id by which the combination is played.
   * @return the id by which the combination was played, and whether the combination was valid or
   *     not. If the player id is not registered in the model an Optional.empty() is returned.
   */
  Optional<GITuple<Integer, Boolean>> play(
      List<GITile> tilesFromHand,
      List<GITile> tilesFromBoard,
      List<List<GITile>> newCombinations,
      Integer id);

  // TODO IS NEW
  Optional<GITuple<Integer, Boolean>> manipulateBoardWith(
      List<GITile> tilesFromHand,
      List<List<GITile>> oldCombinations,
      List<List<GITile>> newCombinations,
      Integer id);

  /**
   * Use this method to get all the tiles on the hand by an id.
   *
   * @param id for which the hand is requested.
   * @return the id by which the hand was requested and all the tiles of the id. If the player id is
   *     not registered in the model an Optional.empty() is returned.
   */
  Optional<GITuple<Integer, List<GITile>>> getAllTilesBy(Integer id);

  /**
   * Use this method to get all the points for a player by its id. It returns its points as a
   * negative.
   *
   * @param id for which the points of the current hand are requested.
   * @return the id for which the points of the hand were requested and the points as negative
   *     GIPoints. If the player id is not registered in the model an Optional.empty() is returned.
   */
  Optional<GITuple<Integer, GIPoints>> calculatePointsBy(Integer id);

  /**
   * Use this method to signal the end of a players turn by its id.
   *
   * @param id the id for which the end of he turn is signaled.
   * @return the id by for which the turn was ended and the whole board. If the player id is not
   *     registered in the model an Optional.empty() is returned.
   */
  Optional<GITuple<Integer, List<List<GITile>>>> finishedTurnBy(Integer id);

  /**
   * Use this method is check if its the first turn of a player.
   *
   * @param id for which is checked whether its the players first turn.
   * @return the id for which this method was called and if its the first turn of the player. If the
   *     player id is not registered in the model an Optional.empty() is returned.
   */
  Optional<GITuple<Integer, Boolean>> isFirstTurnBy(Integer id);

  /**
   * Use this method to calculate the points of the hand for every player registered in the model.
   *
   * @return a tuple with the id and the corresponding points of the players hand.
   */
  Optional<List<GITuple<Integer, GIPoints>>> calculatePointsForRegisteredPlayers();

  /**
   * Use this method to get the id for the player who is in the turn.
   *
   * @return the id of the player.
   */
  Integer getCurrentPlayerId();

  // TODO REMOVE -> testing purpose only
  Optional<GITuple<Integer, List<GITile>>> drawBy(Integer id, List<GITile> customTiles);

  /**
   * Use this method to assign a player with given id its age.
   * @param id of the player whose age is being set.
   * @param age of the player to be set.
   */
  void setAgeFor(Integer id, int age);

  /**
   * Use this method to identify the starting player's id.
   * @return id of the first player.
   */
  Integer getStartingPlayerId();
}
