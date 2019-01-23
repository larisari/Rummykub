package network;

import gameinfo.GIFactory;
import gameinfo.GIGameInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Server {

  static GIGameInfo gameInfo;
  private static Logger log = Logger.getLogger(Server.class.getName());


  public Server() {

    try {
      List<ServerClientCommunication> listOfClients = new ArrayList<ServerClientCommunication>();

      gameInfo = GIFactory.make();
      //System.out.println("Spiel wird geladen....");
      log.info("Spiel wird geladen....");



      ServerListener listener = new ServerListener(listOfClients);
      listener.start();

//      while (game.isAlive()) {
//      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void broadcastToAllClients(String message) {
    List<ServerClientCommunication> clients = ServerListener.getClients();

    for (ServerClientCommunication client : clients) {
      client.sendMessageToClient(message);
    }
  }
}
