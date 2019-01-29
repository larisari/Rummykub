/**
 * The GIGameInfo interface provides all necessary functions for a basic
 * playing experience. Implementing this interface, the user may obtain a
 * central object for managing the whole model. From initializing the players
 * to manipulating the game's progress until creating a valid turn, the
 * GIGameInfo accompanies you through every changes you want to achieve in
 * your model, with the characteristic of keeping the model invisible.
 */
package gameinfo;

import gameinfo.util.GIPoints;
import gameinfo.util.GITile;
import gameinfo.util.GITuple;

import java.util.List;
import java.util.Optional;

public interface GIGameInfo {

  /**
   * Use this method whenever you wish to sign in a player to the game.
   *
   * @param id of the player to be signed in.
   */
  void registerBy(Integer id);

  /**
   * Use this method, whenever you wish to sign out a player from the game.
   *
   * @param id of the player to be signed out.
   */
  void deregisterBy(Integer id);

  /**
   * Use this method to assign a player with given id its age.
   * @param id of the player whose age is being set.
   * @param age of the player to be set.
   */
  void setAgeFor(Integer id, int age);

  /**
   * Use this method to start the game. Keep in mind, that from now on no other
   * player can join the game and sign in.
   */
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
   * Use this method to identify the starting player's id.
   * @return id of the first player.
   */
  Integer getStartingPlayerId();

  /**
   * Use this method to obtain the id of the player whose turn it is.
   *
   * @return the current player's id.
   */
  Integer getCurrentPlayerId();

  /**
   * Use this method to get the Player id of the next valid Player.
   *
   * @return the id of the next valid Player as an Optional.
   */
  Optional<Integer> getNextPlayerId();

  /**
   * Use this method to check whether the passed id matches the current
   * player's id.
   *
   * @param id to be checked.
   * @return a GITuple<id,true> if the player is valid,
   * a GITuple<id,false> if the id does not match and
   * an Optional.empty otherwise.
   */
  Optional<GITuple<Integer, Boolean>> isValidPlayerBy(Integer id);

  /**
   * Use this method is check if it's the first turn of a player.
   *
   * @param id of the player to be checked for first turn.
   * @return the parameter id if it's the first turn of the player.
   * If the player's id is not registered in the model an Optional.empty() is
   * returned.
   */
  Optional<GITuple<Integer, Boolean>> isFirstTurnBy(Integer id);

  /**
   * Use this method to draw a stack (containing 14 Tiles) for a Player id if
   * it's the players first draw or else a single tile. It also automatically
   * ends and calculates the next player's turn.
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
   * @param combinations to be checked for validity.
   * @param id of the player to play the combination.
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
   * Use this method to signal the end of a players turn by its id.
   *
   * @param id the id for which the end of he turn is signaled.
   * @return the id by for which the turn was ended and the whole board. If the player id is not
   *     registered in the model an Optional.empty() is returned.
   */
  Optional<Integer> finishedTurnBy(Integer id);

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
   * Use this method to calculate the points of the hand for every player registered in the model.
   *
   * @return a tuple with the id and the corresponding points of the players hand.
   */
  Optional<List<GITuple<Integer, GIPoints>>> calculatePointsForRegisteredPlayers();

  // TODO REMOVE -> testing purpose only
  Optional<GITuple<Integer, List<GITile>>> drawBy(Integer id, List<GITile> customTiles);

  /**
   * Use this method to obtain the up to date board.
   * @return current board.
   */
  List<List<GITile>> getCurrentBoard();
}
