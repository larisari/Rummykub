package network;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GIPoints;
import gameinfo.util.GITile;
import gameinfo.util.GITuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ServerParser {

  private static Logger log = Logger.getLogger(ServerParser.class.getName());

  public ServerParser() {
  }

  // void parse()
  // Zerlege String -> Modellfunktion
  // Zerlege Result -> String
  // sendReply to Client inkl. broadcast

  static void getStringIntoServerParser(String receivedMessageFromClient, int id) {

    String[] receivedMessage = receivedMessageFromClient.split("[|]");
    List<ServerClientCommunication> clients = ServerListener.getClients();

    switch (receivedMessage[0]) {
      case "draw":
        Optional<GITuple<Integer, List<GITile>>> result = Server.gameInfo.drawBy(id);
        System.out.println(result.get());
        if (result.isPresent()) {

          clients.get(id).sendMessageToClient("responseForDraw|" + parseTileToString(result.get().getSecond()));
          clients.get(Server.gameInfo.getCurrentPlayerId()).sendMessageToClient("itsYourTurn");
        } else {
          // id is not registered in model.
          log.info("There is no " + id + " registered in the model.");
        }
        break;

      case "startGame":
        Server.gameInfo.startGame();
        Server.broadcastToAllClients("responseStartGame");
        Server.broadcastToAllClients("closeStartScreen");
        break;

      case "finishedTurn":
        Optional<GITuple<Integer, List<List<GITile>>>> resultOfTurn = Server.gameInfo.finishedTurnBy(id);

        if (resultOfTurn.isPresent()){
          Server.broadcastToAllClients("responseForFinishedTurn|" + parseCombinationsToString(resultOfTurn.get().getSecond()));
          clients.get(Server.gameInfo.getCurrentPlayerId()).sendMessageToClient("itsYourTurn");
        } else {log.info("There is no " + id + " registered in the model.");}
        break;

      case "getNextPlayerID":
        Optional<Integer> resultID = Server.gameInfo.getNextPlayerId();

        if (resultID.isPresent()) {
          Server.broadcastToAllClients("responseForGetNextPlayerID|" + resultID.get());
        } else {
          // id is not registered in model.
          log.info("There is no " + id + " registered in the model.");
        }
        break;

      case "play":
        String answer = Server.gameInfo.play(parseStringToListListTileComb(receivedMessage[1]), id).get().getSecond().toString();
        clients.get(id).sendMessageToClient("responseForPlay|" + answer);
        break;

      case "playSwapJoker":
        String answer3 = Server.gameInfo.play(parseStringToListListTileComb(receivedMessage[1]), id).get().getSecond().toString();
        clients.get(id).sendMessageToClient("responseForPlaySwapJoker|" + answer3);
        break;

      case "playWithBoardTilesR":
        List<GITile> tilesFromHand = parseStringToTile(receivedMessage[1]);
        List<GITile> tilesFromBoard = parseStringToTile(receivedMessage[2]);
        List<List<GITile>> newCombinations = parseStringToListListTileComb(receivedMessage[3]);

        String answer1 = Server.gameInfo.play(tilesFromHand, tilesFromBoard, newCombinations, id).get().getSecond().toString();
        clients.get(id).sendMessageToClient("responseForPlayWithBoardTilesR|" + answer1);
        break;

      case "playWithBoardTilesL":
        List<GITile> tilesFromHand1 = parseStringToTile(receivedMessage[1]);
        List<GITile> tilesFromBoard1 = parseStringToTile(receivedMessage[2]);
        List<List<GITile>> newCombinations1 = parseStringToListListTileComb(receivedMessage[3]);

        String answer2 = Server.gameInfo.play(tilesFromHand1, tilesFromBoard1, newCombinations1, id).get().getSecond().toString();
        clients.get(id).sendMessageToClient("responseForPlayWithBoardTilesL|" + answer2);
        break;

      case "getPlayerID":
        clients.get(id).sendMessageToClient("responseForGetPlayerID|" + id);
        break;

      case "getPlayerPoints":
        Server.broadcastToAllClients("responseForGetPlayerPoints|" + parsePointsToString(Server.gameInfo.getPlayerPoints()));
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
        for(int i = 0; i < clients.size(); i++){
          if(i != id){
            clients.get(i).sendMessageToClient("responseToNotifyWin");
          }
        }
        break;
    }
  }

  static String parsePointsToString(Optional<List<GITuple<Integer, GIPoints>>> list){
    String points = "";
    List<GITuple<Integer, GIPoints>> tempList = list.get();
    for(int i = 0; i < tempList.size(); i++){
      points += tempList.get(i).getSecond();
      if(i != tempList.size()-1) {
        points += ",";
      }
    }
    return points;
  }

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

  static String parseCombinationsToString(List<List<GITile>> combs){
    String parsedCombs = "list<";

    for(List<GITile> comb : combs){
      parsedCombs += parseTileToString(comb);
    }
    return parsedCombs;
  }

  static List<List<GITile>> parseStringToListListTileComb(String s){
    List<List<GITile>> result = new ArrayList<>();
    s = s.substring(0,s.length()-1);
    String[] split1 = s.split("<");
    String[] split2 = split1[1].split(";");

    for (int i = 0; i < split2.length; i++){
      result.add(parseStringToTile(split2[i]));
    }
    return result;
  }

  //ergebnis in liste packen -> List<List<GITile>>
  public static List<GITile> parseStringToTile(String tiles) {
    List<GITile> tileList = new ArrayList<>();
    String[] comb = tiles.split(":");
    String[] tileS = comb[1].split(",");
    for (int i = 0; i < tileS.length; i++) {
      String[] attributeswS = tileS[i].split("[.]");
      String[] attributes = attributeswS[1].split("/");
      String color = attributes[0];
      String number = attributes[1];
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
          break;
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
        default:
          break;
      }
      GITile tile = new GITile(tileNumber, tileColor);
      tileList.add(tile);
    }
    return tileList;
  }

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
