package de.netzwerk.server;

import de.netzwerk.client.MyClient;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MyServer implements Serializable {

  // Attribute

  int clientCounter;
  private ServerSocket serverSocket;
  private int port;
  private ArrayList<MyClient> clients;
  private Thread listening;
  //  private boolean secureMode;
  //  private boolean stopped;
  private boolean
          safeConnection; // soll alle Clients Verbindung halten (in Liste gespeichert werden)
  private boolean gameStarted = false;

  public MyServer(int port, boolean safeConnection) {
    this.clients = new ArrayList<MyClient>();
    this.port = port;
    this.safeConnection = safeConnection;

    preStart();
  }


  public ArrayList<MyClient> getClients() {
    return clients;
  }

  public void setClients(ArrayList<MyClient> clients) {
    this.clients = clients;
  }

  // Methoden

  /**
   * all Server activities before server starts.
   */
  public void preStart() {
    try {
      System.out.println("Server startet.... Verbindungsaufbau");
      serverSocket = new ServerSocket(48410);
      if (clientCounter < 4) {
        System.out.println("Verbinde mit Client");
        registerClient();
      } else {
        System.out.println("Sorry, es sind bereits 4 Spieler connected... Spiel starten Server!");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void registerClient() {
    if (clientCounter >= 4) {
      System.out.println("Es sind bereits 4 Clients auf dem Server registriert, du kannst der Spielsitzung nicht mehr beitreten");
      return;
    }

    if (gameStarted == true) {
      return;
    }

    try {
      System.out.println("Client wird erstellt....");
      MyClient client = new MyClient("localhost", 48410, clients.size() + 1);
      System.out.println("Client ID:" + client.getId());
      System.out.println("Client loggt sich auf Socket ein.");
      client.login();
      System.out.println("Server versucht Verbindung des Clients zu akzeptieren....");
      Socket remoteClient = serverSocket.accept();
      System.out.println("Check! Verbunden!");

      //      if (client.getSocket().isConnected())

      clients.add(client);
      clientCounter++;
      System.out.println(
              "Server hat Client Liste hinzugefügt, Socket: " + clients.get(0).getSocket());
      System.out.println("Client-Counter: " + clientCounter);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** listening to all open Threads and react to inquiries. */
  public void startListening() {

  }

  /**
   * @param socket
   * @param clientID
   */
  public void sendReply(Socket socket, int clientID) {}

  /**
   * @param clientID
   * @param DatapackageID
   * @param datapackage
   */
  public void sendMessage(int clientID, String DatapackageID, Object datapackage) {
  }

  //  private InetAddress address;
  //
  //
  //  private boolean isRunning = true;
  //
  //  public MyServer(int port) {
  //    this.port = port;
  //  }
  //
  //
  //  public static void main(String[] args) {
  //    MyServer server = new MyServer(48410);
  //    server.startListening(); // damit wird Thread gestartet
  //  }
  //
  //  // darauf starten wir eigenen Thread -> Grund: ist kein eigenes Programm, sondern in unserem
  // Spiel
  //  // soll Server im Hintergrund laufen
  //  public void startListening() {
  //    new Thread(
  //            new Runnable() {
  //              @Override
  //              public void run() {
  //
  //                while (isRunning) {
  //
  //
  //
  //                  try {
  //                    System.out.println("[Server] Server starten.....");
  //                    serverSocket = new ServerSocket(port); // = Server
  //                    address = serverSocket.getInetAddress();
  //
  //
  //                    System.out.println("Server Inet Adresse"+address.getHostAddress());
  //
  //                    // Socket = Schnittstelle zum Client
  //
  //                    System.out.println("[Server] Warten auf Verbindung....");
  //
  //                    Socket remoteClient = serverSocket.accept(); // entfernter Client
  //                    // an dieser Stelle wird das Programm solange geblockt, bis ein ClientSocket
  //                    //                                 sich an dem ServerPort anmeldet
  //                    System.out.println(
  //                        "[Server] Client verbunden: "
  //                            + remoteClient
  //                                .getRemoteSocketAddress()); // damit wir sehen, woher Client
  // sich
  //                    //                                 verbunden hat
  //                    // hier wird Verbindung dann verarbeitet
  //
  //
  //                    System.out.println("Remote-Client INet-Adresse" +
  // remoteClient.getInetAddress());
  //                    System.out.println("Remote-Client Remote Socket
  // Address"+remoteClient.getRemoteSocketAddress());
  //                    System.out.println("Remote-Client
  // LocalSocketAddress"+remoteClient.getLocalSocketAddress());
  //                    System.out.println("Remote-Client Channel"+remoteClient.getChannel());
  //
  //                    // Input, OutputStream aus anderem Programm besser, nur zum Verständnis
  //                    Scanner scanner =
  //                        new Scanner(
  //                            new BufferedReader(
  //                                new InputStreamReader(remoteClient.getInputStream())));
  //                    if (scanner.hasNextLine()) {
  //                      System.out.println("[Server] Message from Client: " + scanner.nextLine());
  //                    }
  //
  //                    // Anfrage an Model weitergeben
  //                    // Antwort an Client zurückgeben
  //
  //                    // Verbindung danach wieder schließen
  //                    scanner.close();
  //                    remoteClient.close();
  //                    serverSocket.close();
  //
  //                  } catch (IOException e) {
  //                    e.printStackTrace();
  //                  }
  //                }
  //              }
  //            }).start();
  //  }

}
