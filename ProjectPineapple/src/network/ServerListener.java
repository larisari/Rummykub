package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerListener extends Thread {

  static List<ServerClientCommunication> clients;
  boolean isRunning = true;
  private int clientID = 0;

  public ServerListener(List<ServerClientCommunication> listOfClients) {
    clients = listOfClients;
    System.out.println("Listener Start");
  }

  public static List<ServerClientCommunication> getClients() {
    return clients;
  }

  @Override
  public void run() {
    // server is listening on port 48410
    try {
      ServerSocket ss = new ServerSocket(48410);
      System.out.println("[Server] wird initialisiert...");
      // running infinite loop for getting
      // client request

      while (isRunning) {
        try {
          if (clientID < 4) {
            System.out.println("[Server] Warte auf eingehende Verbindung....");
            Socket s = ss.accept();

            System.out.println("[Server] neuer Client wurde aufgenommen " + s);

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            System.out.println("[Server] erstelle eigenen Thread für Client " + clientID);
            // create a new thread object
            ServerClientCommunication t = new ServerClientCommunication(s, in, out, clientID);

            t.sendMessageToClient("addJoined");

            // Invoking the start() method
            t.start();

            clients.add(t);
            Server.gameInfo.registerBy(clientID);
            //Nachricht an Host Client, um Start Button zu aktivieren
            if(clients.size() == 2) {
              clients.get(0).sendMessageToClient("possibleToStart");
            }
            clientID++;
          } else {
            isRunning = false;
          }
        } catch (Exception e) {
          //s.close();
          System.out.println("[Server] Verbindung getrennt.");
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void broadcastToAllClients(String message) {
    for (ServerClientCommunication client : clients) {
      client.sendMessageToClient(message);
    }
  }

}
