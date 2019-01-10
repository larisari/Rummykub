package de.netzwerk.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer implements Serializable {

  private int port;
  private boolean isRunning = true;
  // Konstruktor braucht einen Port
  public MyServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) {
    MyServer server = new MyServer(48410);
    server.startListening(); // damit wird Thread gestartet
  }

  // darauf starten wir eigenen Thread -> Grund: ist kein eigenes Programm, sondern in unserem Spiel
  // soll Server im Hintergrund laufen
  public void startListening() {
    new Thread(
            new Runnable() {
              @Override
              public void run() {

                while (isRunning) {

                  try {
                    System.out.println("[Server] Server starten.....");
                    ServerSocket serverSocket = new ServerSocket(port); // = Server

                    // Socket = Schnittstelle zum Client

                    System.out.println("[Server] Warten auf Verbindung....");

                    Socket remoteClient = serverSocket.accept(); // entfernter Client
                    // an dieser Stelle wird das Programm solange geblockt, bis ein ClientSocket
                    //                                 sich an dem ServerPort anmeldet
                    System.out.println(
                        "[Server] Client verbunden: "
                            + remoteClient
                                .getRemoteSocketAddress()); // damit wir sehen, woher Client sich
                    //                                 verbunden hat
                    // hier wird Verbindung dann verarbeitet

                    // Input, OutputStream aus anderem Programm besser, nur zum Verständnis
                    Scanner scanner =
                        new Scanner(
                            new BufferedReader(
                                new InputStreamReader(remoteClient.getInputStream())));
                    if (scanner.hasNextLine()) {
                      System.out.println("[Server] Message from Client: " + scanner.nextLine());
                    }

                    // Anfrage an Model weitergeben
                    // Antwort an Client zurückgeben

                    // Verbindung danach wieder schließen
                     scanner.close();
                     remoteClient.close();
                     serverSocket.close();

                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                }
              }
            })
        .start();
  }
}
