package network;

import java.util.List;

public class ServerParser {

  private static String recievedMessageFromAClient;
  private static String sendMessage;

  public ServerParser() {
  }


  // void parse()
  //Zerlege String -> Modellfunktion
  //Zerlege Result -> String
  //sendReply to Client inkl. broadcast


  public static void getStringIntoServerParser(String receivedMessageFromClient, int id) {
    // receivedMessageFromClient = recievedMessageFromAClient;
    getResultFromModel(receivedMessageFromClient, id);
  }

  public static void getResultFromModel(String incomingClientMessage, int id) {




    if (incomingClientMessage.equals("gibStein")) {
      List<ServerClientCommunication> allClients = ServerListener.getClients();
      allClients.get(id).sendMessageToClient(testRückgabe());
    }
  }




  public static String testRückgabe() {
    return "ERFOLG!";
  }

  //StringtoModel


  //ModeltoString
//  public static void updateSe
//
//
//
//  String inputModel = "endturn";
//  switch (inputModel) {
//
//    case "endturn" :
//
//      toreturn = fordate.format(date);
//      dos.writeUTF(toreturn);
//      break;
//
//    case "Time" :
//      toreturn = fortime.format(date);
//      dos.writeUTF(toreturn);
//      break;
//
//    default:
//      dos.writeUTF("Invalid input");
//      break;
//  }

}
