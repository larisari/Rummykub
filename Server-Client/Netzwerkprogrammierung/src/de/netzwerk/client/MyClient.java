package de.netzwerk.client;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class MyClient implements Serializable {
  private SocketAddress address; // auf dieser Addresse verbindet sich später der Client
  private Socket socket;
  private int port;
  private int id;


  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  /**
   * Constructor
   *
   * @param hostname (IP-Adresse des Servers)
   * @param port (Port, mit dem man den Server erreicht)
   */
  public MyClient(String hostname, int port, int id, Socket socket) {

    address =
        new InetSocketAddress(
            hostname, port); // hostname entspricht dem Rechner auf dem der Server läuft
    this.id = id;
    this.socket = socket;
  }

//  /**
//   * main-Methode, der einen Client initialisiert und eine Nachricht an den Server sendet.
//   *
//   * @param args
//   */
//  public static void main(String[] args) {
//    // if ( neues Spiel gehostet ...
//    MyClient client =
//        new MyClient(
//            "localhost",
//            48410); // Hostaddresse muss geändert werden, localhost ist immer eigener Rechner
//    client.sendMessage("Client sagt: Hallo Server!");
//
//    // Spielbeitritt
//    // else
//    // MyClient remoteClient = new MyClient(server.getLocalHost(), 48410);
//  }
//
//  /**
//   * Methode, zum Senden einer Nachricht an den Server
//   *
//   * @param msg message, die an den Server geschickt wird
//   */
//  public void sendMessage(String msg) {
//
//    try {
//      System.out.println("[Client] Verbinde zu Server....");
//      Socket socket = new Socket();
//      socket.connect(address, 5000);
//      System.out.println("[Client] Verbunden!");
//
//      // nun müssen wir Daten schreiben
//
//      System.out.println("[Client] Sende Nachricht....");
//      PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//      pw.println(msg);
//      pw.flush(); // Daten dann abrauschen durch die Leitung
//      System.out.println("[Client] Nachricht gesendet!");
//
//      // Verbindung wieder schließen
//      pw.close();
//      socket.close();
//
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//
//
//
//
//  public void connectToOwnServer() {
//    client = new MyClient("localhost", 48410);
//    socket = new Socket();
//    try {
//      socket.connect(address, 5000);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  public void joinToOtherServer(SocketAddress serverAdress) {
//    client = new MyClient("IP-Adresse über Getter aus Serverklasse holen", 48410);
//    socket = new Socket();
//    try {
//      socket.connect(serverAdress);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  public void disconnect() {
//    try {
//      socket.close();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  public boolean isConnected() {
//    return !socket.isClosed();
//  }
}
