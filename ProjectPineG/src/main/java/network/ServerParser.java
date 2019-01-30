package network;

import gameinfo.GIFactory;
import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GIPoints;
import gameinfo.util.GITile;
import gameinfo.util.GITuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/** Decomposition and conversion of an input in a suitable for further processing format. */
public class ServerParser {

  private static Logger log = Logger.getLogger(ServerParser.class.getName());

  /**
   * Dissmantles String message from client and selects the right method from gameinfo to answer to
   * clients requests.
   *
   * @param receivedMessageFromClient incoming message from client.
   * @param id client ID.
   */
  static void getStringIntoServerParser(String receivedMessageFromClient, int id) {

    String[] receivedMessage = receivedMessageFromClient.split("[|]");
    List<ServerClientCommunication> clients = ServerListener.getClients();

    switch (receivedMessage[0]) {
      case "draw":
        Optional<GITuple<Integer, List<GITile>>> result = Server.gameInfo.drawBy(id);
        List<List<GITile>> boardTiles = Server.gameInfo.getCurrentBoard();
        if (result.isPresent()) {

          clients
              .get(id)
              .sendMessageToClient(
                  "responseForDraw|" + parseTileToString(result.get().getSecond()));
          Server.broadcastToAllClients(
              "responseForFinishedTurn|" + parseCombinationsToString(boardTiles));
          clients.get(Server.gameInfo.getCurrentPlayerId()).sendMessageToClient("itsYourTurn");
          Server.broadcastToAllClients(
              "UpdateCurrentPlayerTurn|" + Server.gameInfo.getCurrentPlayerId());

          System.out.println(
              "SERVER: pressed draw by "
                  + id
                  + " next player "
                  + Server.gameInfo.getCurrentPlayerId());
        } else {
          log.info("There is no " + id + " registered in the model.");
        }
        break;

      case "finishedTurn":
        Server.gameInfo.finishedTurnBy(id);
        List<List<GITile>> resultOfTurn = Server.gameInfo.getCurrentBoard();

        Server.broadcastToAllClients(
            "responseForFinishedTurn|" + parseCombinationsToString(resultOfTurn));
        clients.get(Server.gameInfo.getCurrentPlayerId()).sendMessageToClient("itsYourTurn");
        Server.broadcastToAllClients(
            "UpdateCurrentPlayerTurn|" + Server.gameInfo.getCurrentPlayerId());

        break;

      case "getNextPlayerID":
        Integer resultID = Server.gameInfo.getCurrentPlayerId();
        Server.broadcastToAllClients("responseForGetNextPlayerID|" + resultID);
        break;

      case "startGame":
        Server.gameInfo.startGame();
        for (ServerClientCommunication client : clients) {
          client.sendMessageToClient(
              "responseStartGame|" + clients.size() + "|" + client.getClientID());
        }
        int clientID = Server.gameInfo.getStartingPlayerId();
        clients.get(clientID).sendMessageToClient("itsYourTurn");
        Server.broadcastToAllClients(
            "UpdateCurrentPlayerTurn|" + Server.gameInfo.getStartingPlayerId());

        break;

      case "play":
        String answer =
            Server.gameInfo
                .play(parseStringToListListTileComb(receivedMessage[1]), id)
                .get()
                .getSecond()
                .toString();
        clients.get(id).sendMessageToClient("responseForPlay|" + answer);
        break;

      case "playSwapJoker":
        List<GITile> tileFromHandJ = parseStringToTile(receivedMessage[1]);
        List<List<GITile>> oldCombinationJ = parseStringToListListTileComb(receivedMessage[2]);
        List<List<GITile>> newCombinationJ = parseStringToListListTileComb(receivedMessage[3]);
        String answer3 =
            Server.gameInfo
                .manipulateBoardWith(tileFromHandJ, oldCombinationJ, newCombinationJ, id)
                .get()
                .getSecond()
                .toString();
        clients.get(id).sendMessageToClient("responseForPlaySwapJoker|" + answer3);
        break;

      case "playHandWithBoard":
        List<GITile> tilesFromHandB = parseStringToTile(receivedMessage[1]);
        List<List<GITile>> oldCombinationsB = parseStringToListListTileComb(receivedMessage[2]);
        List<List<GITile>> newCombinationsB = parseStringToListListTileComb(receivedMessage[3]);
        String answer5 =
            Server.gameInfo
                .manipulateBoardWith(tilesFromHandB, oldCombinationsB, newCombinationsB, id)
                .get()
                .getSecond()
                .toString();
        clients.get(id).sendMessageToClient("responseForPlayHandWithBoard|" + answer5);
        break;

      case "playWithBoardTilesL":
        List<GITile> tilesFromHand1 = parseStringToTile(receivedMessage[1]);
        List<GITile> tilesFromBoard2 = parseStringToTile(receivedMessage[2]);
        List<List<GITile>> CombinationsOnBoard = parseStringToListListTileComb(receivedMessage[3]);

        String answer2 =
            Server.gameInfo
                .play(tilesFromHand1, tilesFromBoard2, CombinationsOnBoard, id)
                .get()
                .getSecond()
                .toString();
        clients.get(id).sendMessageToClient("responseForPlayWithBoardTilesL|" + answer2);
        break;

      case "playWithBoardTilesR":
        List<GITile> tilesFromHand = parseStringToTile(receivedMessage[1]);
        List<GITile> tilesFromBoard = parseStringToTile(receivedMessage[2]);
        List<List<GITile>> newCombinations = parseStringToListListTileComb(receivedMessage[3]);

        String answer1 =
            Server.gameInfo
                .play(tilesFromHand, tilesFromBoard, newCombinations, id)
                .get()
                .getSecond()
                .toString();
        clients.get(id).sendMessageToClient("responseForPlayWithBoardTilesR|" + answer1);
        break;

      case "calculatePointsForRegisteredPlayers":
        clients
            .get(id)
            .sendMessageToClient(
                "responseForGetPlayerPoints|"
                    + parsePointsToString(Server.gameInfo.calculatePointsForRegisteredPlayers()));
        break;

      case "numberOfPlayers":
        Optional<Integer> resultNumber = Server.gameInfo.getNumberOfPlayers();
        if (resultNumber.isPresent()) {
          clients.get(id).sendMessageToClient("responseForNumberOfPlayers|" + resultNumber.get());
        } else {
          // id is not registered in model.
          log.info("There is no " + id + " registered in the model.");
        }
        break;

      case "notifyWin":
        for (int i = 0; i < clients.size(); i++) {
          if (i != id) {
            clients
                .get(i)
                .sendMessageToClient(
                    "responseToNotifyWin|"
                        + parsePointsToString(
                            Server.gameInfo.calculatePointsForRegisteredPlayers()));
          }
        }
        break;
      case "setAge":
        int age = Integer.parseInt(receivedMessage[1]);
        Server.gameInfo.setAgeFor(id, age);
        break;

      case "newGame":
        Server.gameInfo = GIFactory.make();
        for (ServerClientCommunication client : clients) {
          Server.gameInfo.registerBy(client.getClientID());
        }
        break;
    }
  }

  /**
   * Converts points of all clients to a String.
   * @param list list-tupel with clientID and points.
   * @return a representive String with points of all clients.
   */
  static String parsePointsToString(Optional<List<GITuple<Integer, GIPoints>>> list) {
    String points = "";
    List<GITuple<Integer, GIPoints>> tempList = list.get();
    for (int i = 0; i < tempList.size(); i++) {
      System.out.println(tempList.get(i).getSecond().value());
      points += tempList.get(i).getSecond().value();
      if (i != tempList.size() - 1) {
        points += ",";
      }
    }
    System.out.println(points + "PARSED FROM GIPOINTS TO STRING");
    return points;
  }

  /**
   * Converts a List of tiles to String.
   * @param tiles list of tiles.
   * @return a representive String with all tiles.
   */
  static String parseTileToString(List<GITile> tiles) {
    String parsedTiles = "comb:";

    for (int i = 0; i < tiles.size(); i++) {
      String colorNumber =
          parseColorToString(tiles.get(i).getColor(), tiles.get(i).getNumber().value());
      parsedTiles += colorNumber;
    }
    parsedTiles += ";";
    return parsedTiles;
  }

  /**
   * converts a combination list to String.
   * @param combs combination list with a list of tiles.
   * @return a representive String of combination.
   */
  static String parseCombinationsToString(List<List<GITile>> combs) {
    String parsedCombs = "list<";

    for (List<GITile> comb : combs) {
      parsedCombs += parseTileToString(comb);
    }
    System.out.println(parsedCombs + " BOARDCOMBS");
    return parsedCombs;
  }

  /**
   *parses String to list of combinations.
   * @param s received string.
   * @return list of combinations.
   */
  static List<List<GITile>> parseStringToListListTileComb(String s) {
    List<List<GITile>> result = new ArrayList<>();
    //    s = s.substring(0,s.length()-1);
    String[] split1 = s.split("[<]");
    String[] split2 = split1[1].split("[;]");

    for (int i = 0; i < split2.length; i++) {
      result.add(parseStringToTile(split2[i]));
    }
    return result;
  }

  /**
   * converts a String to a list of tiles.
   * @param tiles String of all tiles.
   * @return a list of tiles.
   */
  public static List<GITile> parseStringToTile(String tiles) {
    List<GITile> tileList = new ArrayList<>();

    if (tiles.length() > 0 && tiles.charAt(tiles.length() - 1) == ';') {
      tiles = tiles.substring(0, tiles.length() - 1);
    }

    log.info("All tiles that were passed " + tiles);

    String[] comb = tiles.split("[:]");
    log.info("All combinations in tiles: " + comb.length);
    for (String combTile : comb) {
      log.info(combTile);
    }

    String[] tileS = comb[1].split("[,]");
    log.info("All tiles: " + tileS.length);
    for (String tile : tileS) {
      log.info(tile);
    }
    for (int i = 0; i < tileS.length; i++) {
      String[] attributeswS = tileS[i].split("[.]");
      String[] attributes = attributeswS[1].split("[/]");
      String color = attributes[0];
      String number = attributes[1];

      log.info("COLOR ATTRIBUTE " + color);
      log.info("NUMBER ATTRIBUTE " + number);

      GIColor tileColor = null;
      GINumber tileNumber = null;

      switch (color) {
        case "blue":
          tileColor = GIColor.BLUE;
          break;
        case "red":
          tileColor = GIColor.RED;
          break;
        case "yellow":
          tileColor = GIColor.YELLOW;
          break;
        case "black":
          tileColor = GIColor.BLACK;
          break;
        case "joker":
          tileColor = GIColor.JOKER;
          break;
        default:

      }

      switch (number) {
        case "1":
          tileNumber = GINumber.ONE;
          break;
        case "2":
          tileNumber = GINumber.TWO;
          break;
        case "3":
          tileNumber = GINumber.THREE;
          break;
        case "4":
          tileNumber = GINumber.FOUR;
          break;
        case "5":
          tileNumber = GINumber.FIVE;
          break;
        case "6":
          tileNumber = GINumber.SIX;
          break;
        case "7":
          tileNumber = GINumber.SEVEN;
          break;
        case "8":
          tileNumber = GINumber.EIGHT;
          break;
        case "9":
          tileNumber = GINumber.NINE;
          break;
        case "10":
          tileNumber = GINumber.TEN;
          break;
        case "11":
          tileNumber = GINumber.ELEVEN;
          break;
        case "12":
          tileNumber = GINumber.TWELVE;
          break;
        case "13":
          tileNumber = GINumber.THIRTEEN;
          break;
        case "joker":
          tileNumber = GINumber.JOKER;
          break;
        default:
          //    throw new FatalError("SOMETHING WENT DEEPLY WRONG " + tileColor);
      }
      GITile tile = new GITile(tileNumber, tileColor);
      tileList.add(tile);
    }
    for (GITile tile : tileList) {
      System.out.println(tile);
    }
    return tileList;
  }

  /**
   * converts a color to String.
   * @param color color.
   * @param number if number equals 0 its a joker tile.
   * @return a representive String of a color.
   */
  private static String parseColorToString(GIColor color, int number) {
    StringBuilder builder = new StringBuilder();
    builder.append("tile.");

    switch (color) {
      case BLUE:
        builder.append("blue");
        break;
      case RED:
        builder.append("red");
        break;
      case BLACK:
        builder.append("black");
        break;
      case YELLOW:
        builder.append("yellow");
        break;
      case JOKER:
        builder.append("joker");
        break;
      default:
        break;
    }

    builder.append("/");

    if (number == 0) {
      builder.append("joker");
    } else {
      builder.append(number);
    }
    builder.append(",");

    return builder.toString();
  }
}
