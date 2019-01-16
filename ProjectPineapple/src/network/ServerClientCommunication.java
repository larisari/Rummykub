package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerClientCommunication extends Thread {

  private final DataInputStream in;
  private final DataOutputStream out;
  private final Socket s;
  private final int clientID;


  public ServerClientCommunication(Socket s, DataInputStream in, DataOutputStream out, int clientID) {
    this.in = in;
    this.out = out;
    this.s = s;
    this.clientID = clientID;
  }

  // Startet Thread und "lauscht" innerhalb der while-Schleife ununterbrochen auf eingehende Nachricht vom Client.
  @Override
  public void run() {

    while (true) {
      String received = null;

      try {
        received = in.readUTF();
        System.out.println("[Server] Nachricht von " + clientID + "erhalten:" + received);
        System.out.println("[Server] Habe die Nachricht an den Parser übergeben....");
        ServerParser.getStringIntoServerParser(received, clientID);


      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("No inputconnection to client: " + clientID);
      }
      if (received != null) {

      }
    }
  }

  public void sendMessageToClient(String message) {
    String messageToClient = message;
    try {

      out.writeUTF(messageToClient);
      System.out.println("[Server] Nachricht an Client " + clientID + "geschickt.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void disconnectClient() {
    try {
      in.close();
      out.close();
      s.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
