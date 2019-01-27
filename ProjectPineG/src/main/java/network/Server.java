package network;

import gameinfo.GIFactory;
import gameinfo.GIGameInfo;

import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Server {
  static GIGameInfo gameInfo;
  private static Logger log = Logger.getLogger(Server.class.getName());


  /**
   * Constructor which initialize the gameInfo from our game model ands start the Server Listener Thread.
   */
  public Server() {



    try {
      List<ServerClientCommunication> listOfClients = new ArrayList<ServerClientCommunication>();

      gameInfo = GIFactory.make();
      log.info("Spiel wird geladen....");

      ServerListener listener = new ServerListener(listOfClients);
      listener.start();


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * sends a message to all connected clients.
   * @param message message, server sends to clients.
   */
  public static void broadcastToAllClients(String message) {
    List<ServerClientCommunication> clients = ServerListener.getClients();


    for (ServerClientCommunication client : clients) {
      client.sendMessageToClient(message);
    }
  }

}
