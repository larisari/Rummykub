package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Logger;

/** Server Thread, which opens the Server and handles incoming client connections. */
public class ServerListener extends Thread {

  private static List<ServerClientCommunication> clients;
  private static int clientID;
  private static Logger log = Logger.getLogger(ServerListener.class.getName());
  private static ServerSocket ss;
  private boolean isRunning = true;

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
  }

  /**
   * Getter of all registered clients.
   *
   * @return a list of all registered clients.
   */
  static List<ServerClientCommunication> getClients() {
    return clients;
  }

  /**
   * Interrupts the running Server Thread and closes the serverSocket.
   */
  private static void killServer() {
    try {
      log.info("[Server] Thread wird unterbrochen.");
      currentThread().interrupt();
      log.info("[Server] Serversocket wird geschlossen.");
      ss.setReuseAddress(true);
      ss.close();

      log.info("[Server] Server wurde geschlossen.");
    } catch (IOException e) {
      log.info("[Server] kann nicht geclosed werden.");
      e.printStackTrace();
    }
  }

  /**
   * removes all clients and closes the server.
   */
  private static void restartListener() {
    for (int i = clients.size() - 1; i >= 0; i--) {
      clients.remove(i);
    }
    killServer();
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
            System.out.println("[Server] Connection aufgebaut  " + s.isConnected());

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out =
                new DataOutputStream(
                    s.getOutputStream()); // hier ist Client-Objekt dann vollständig connected

            log.info("[Server] erstelle eigenen Thread für Client " + clientID);
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
        } catch (SocketException e) {
          log.info("[Server] Verbindung zu Client " + clientID + " verloren...");

          getThreadGroup().destroy();
          this.interrupt();
          log.info("Alle Client Verbindungen wurden gekillt.");
          restartListener();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
