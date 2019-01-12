package de.netzwerk.server;

import de.netzwerk.client.MyClient;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MyServer implements Serializable {

  //Attribute

  private ServerSocket serverSocket;
  private int port;
  private ArrayList<MyClient> clients;
  private Thread listening;
  private boolean safeConnection; //soll alle Clients Verbindung halten (in Liste gespeichert werden)
//  private boolean secureMode;
//  private boolean stopped;

  int clientCounter;




  //Konstruktoren

  public MyServer(int port, boolean safeConnection) {
    this.clients = new ArrayList<MyClient>();
    this.port = port;
    this.safeConnection = safeConnection;

    preStart();

  }

  //Methoden


  /**
   * all Server activities before server starts.
   */
  public void preStart() {
    MyServer server = new MyServer(48410, true);
    try{
      serverSocket = new ServerSocket(48410);
      if(clientCounter < 4) {
        registerClient();
      } else {
        System.out.println("Sorry, es sind bereits 4 Spieler connected... Spiel starten Server!");
      }


    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void registerClient(){
    try{
      Socket remoteClient = serverSocket.accept();
      if(remoteClient.isConnected()) {
        MyClient client = new MyClient("localhost", 48410, clients.size()+1, remoteClient); //

        clients.add(client);
        clientCounter++;
        }
    }catch (IOException e) {
      e.printStackTrace();
    }
  }



  /**
   * listening to all open Threads and react to inquiries.
   */
  public void startListening() {

  }

  /**
   *
   * @param socket
   * @param clientID
   */
  public void sendReply(Socket socket, int clientID) {

  }

  /**
   *
   * @param clientID
   * @param DatapackageID
   * @param datapackage
   */
  public void sendMessage(int clientID, String DatapackageID, Object datapackage){

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
//  // darauf starten wir eigenen Thread -> Grund: ist kein eigenes Programm, sondern in unserem Spiel
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
//                                .getRemoteSocketAddress()); // damit wir sehen, woher Client sich
//                    //                                 verbunden hat
//                    // hier wird Verbindung dann verarbeitet
//
//
//                    System.out.println("Remote-Client INet-Adresse" + remoteClient.getInetAddress());
//                    System.out.println("Remote-Client Remote Socket Address"+remoteClient.getRemoteSocketAddress());
//                    System.out.println("Remote-Client LocalSocketAddress"+remoteClient.getLocalSocketAddress());
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
