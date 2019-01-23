package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * The Server Listener starts the Server and handles incoming client-connection.
 */
public class ServerListener extends Thread {

  static List<ServerClientCommunication> clients;
  boolean isRunning = true;
  private int clientID = 0;
  private static Logger log = Logger.getLogger(ServerListener.class.getName());

  public ServerListener(List<ServerClientCommunication> listOfClients) {
    clients = listOfClients;
    log.info("Listener Start...");
    //System.out.println("Listener Start");
  }

  public static List<ServerClientCommunication> getClients() {
    return clients;
  }

  @Override
  public void run() {

    try {
      ServerSocket ss = new ServerSocket(48410);

      log.info("[Server] wird initialisiert...");


      while (isRunning) {
        try {
          if (clientID < 4) {

            log.info("[Server] Warte auf eingehende Verbindung....");
            Socket s = ss.accept();


            log.info("[Server] neuer Client wurde aufgenommen " + s);

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());


            log.info("[Server] erstelle eigenen Thread für Client " + clientID);
            // create a new thread object
            ServerClientCommunication t = new ServerClientCommunication(s, in, out, clientID);

            // Invoking the start() method
            t.start();

            clients.add(t);
            Server.gameInfo.registerBy(clientID);
            //Nachricht an Host Client, um Start Button zu aktivieren
            if(clients.size() == 2) {
              clients.get(0).sendMessageToClient("possibleToStart");
            }
            clients.get(clients.size()-1).sendMessageToClient("loadLoadingScreen|" + clientID);
            Server.broadcastToAllClients("addJoined|" + clientID);
            clientID++;

          } else {
            isRunning = false;
          }
        } catch (Exception e) {
          //s.close();
          log.info("[Server] Verbindung getrennt.");
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
