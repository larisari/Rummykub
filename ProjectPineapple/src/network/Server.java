package network;

import java.util.ArrayList;
import java.util.List;
import gameinfo.GIGameInfo;
import gameinfo.GIFactory;

public class Server {

  public Server() {


    try {
      List<ServerClientCommunication> listOfClients = new ArrayList<ServerClientCommunication>();

      GIGameInfo game = GIFactory.make();
      System.out.println("Spiel wird geladen....");
      game.start();

      ServerListener listener = new ServerListener(listOfClients);
      listener.start();


//      while (game.isAlive()) {
//      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void broadcastToAllClients(String message) {
    List<ServerClientCommunication> clients = ServerListener.getClients();

    String command = "";

    switch (command) {
      case "finish":
    }
    for (ServerClientCommunication client : clients) {
      client.sendMessageToClient(message);
    }
  }
}
