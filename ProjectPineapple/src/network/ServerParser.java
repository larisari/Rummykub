package network;

import gameinfo.util.GIColor;
import gameinfo.util.GIPoints;
import gameinfo.util.GITile;
import gameinfo.util.GITuple;

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
        if (result.isPresent()) {
          clients.get(id).sendMessageToClient("responseForDraw|" + parseTileToString(result.get().getSecond()));
        } else {
          // id is not registered in model.
          log.info("There is no " + id + " registered in the model.");
        }
        break;

      case "startGame":
        Server.gameInfo.startGame();
        Server.broadcastToAllClients("responseStartGame");
        break;

      case "finishedTurn":
        Optional<GITuple<Integer, List<List<GITile>>>> resultOfTurn = Server.gameInfo.finishedTurnBy(id);
        Integer currentPlayerID = Server.gameInfo.getCurrentPlayerId();

        if (resultOfTurn.isPresent()){
          Server.broadcastToAllClients("responseForFinishedTurn|" + parseCombinationsToString(resultOfTurn.get().getSecond()));
          clients.get(currentPlayerID).sendMessageToClient("itsYourTurn");
        } else {log.info("There is no " + id + " registered in the model.");}
        break;

      case "getNextPlayerID":
        Optional<Integer> resultID = Server.gameInfo.getNextPlayerId();

        if (resultID.isPresent()) {
          clients.get(id).sendMessageToClient("responseForGetNextPlayerID|" + resultID.get());
        } else {
          // id is not registered in model.
          log.info("There is no " + id + " registered in the model.");
        }
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
