package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/** Server Thread, which opens the Server and handles incoming client connections. */
public class ServerListener extends Thread {

  static boolean isRunning;
  private static List<ServerClientCommunication> clients;
  private static int clientID;
  private static Logger log = Logger.getLogger(ServerListener.class.getName());
  private static ServerSocket ss;

  /**
   * Constructor, which safes all clients and sets an ID according to the number of registered
   * clients.
   *
   * @param listOfClients List of all connected clients.
   */
  ServerListener(List<ServerClientCommunication> listOfClients) {
    clients = listOfClients;
    clientID = clients.size();
    log.info("Listener Start...");
    isRunning = true;
  }

  /**
   * Getter of all registered clients.
   *
   * @return a list of all registered clients.
   */
  static List<ServerClientCommunication> getClients() {
    return clients;
  }

  /** Interrupts the running Server Thread and closes the serverSocket. */
  private static void killServer() {
    try {
      log.info("[Server] Serversocket wird geschlossen.");
      ss.close();
      log.info("[Server] Server wurde geschlossen.");
    } catch (IOException e) {
      log.info("[Server] kann nicht geclosed werden.");
      e.printStackTrace();
    }
  }

  /** removes all clients and closes the server. */
  private static void restartListener() {

    clients.forEach(
        client -> {
          try {
            client.getS().close();
            client.getIn().close();
            client.getOut().close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });

    killServer();
    clients.clear();
    log.info("[ServerListener] Starting Client ID after Restart..." + clientID);
  }

  @Override
  public void run() {

    try {

      ss = new ServerSocket(48410);

      log.info("[Server] wird initialisiert...");

      while (isRunning) {
        try {
          if (clientID < 4) {

            log.info("[Server] Warte auf eingehende Verbindung....");
            Socket s = ss.accept();

            log.info("[Server] neuer Client wurde aufgenommen " + s);

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out =
                new DataOutputStream(
                    s.getOutputStream()); // hier ist Client-Objekt dann vollst??ndig connected

            log.info("[Server] erstelle eigenen Thread f??r Client " + clientID);
            // create a new thread object
            ServerClientCommunication t = new ServerClientCommunication(s, in, out, clientID);

            t.start();

            clients.add(t);
            Server.gameInfo.registerBy(clientID);

            if (clients.size() == 2) {
              clients.get(0).sendMessageToClient("possibleToStart");
            }
            clients.get(clients.size() - 1).sendMessageToClient("loadLoadingScreen|" + clientID);
            Server.broadcastToAllClients("addJoined|" + clientID);
            clientID++;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      // happens as soon as ServerListener is not running.
      restartListener();
      interrupt();

    } catch (IOException e) {
      log.info("[ServerListener] yes, hier fliegt auch die IO Exception.");
      e.printStackTrace();
    }
  }
}
