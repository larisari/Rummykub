import java.util.ArrayList;
import java.util.List;

public class Server {

  public Server(){




    try {
      List<ServerClientCommunication> listOfClients = new ArrayList<ServerClientCommunication>();

      GameInfoImpl game = new GameInfoImpl(listOfClients);
      System.out.println("Spiel wird geladen....");
      game.start();

      ServerListener listener = new ServerListener(listOfClients);
      listener.start();




      while (game.isAlive()){
      }
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  public void broadcastToAllClients(String message){
    List<ServerClientCommunication> clients = ServerListener.getClients();

    String command = "";

    switch(command) {
      case "finish":
    }
    for (ServerClientCommunication client : clients){
      client.sendMessageToClient(message);
    }
  }
}
