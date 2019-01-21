package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerClientCommunication extends Thread {

  private final DataInputStream in;
  private final DataOutputStream out;
  private final Socket s;
  private final int clientID;
  private static Logger log = Logger.getLogger(ServerClientCommunication.class.getName());


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
        //System.out.println("[Server] Nachricht von " + clientID + "erhalten:" + received);
        log.info("[Server] Nachricht von " + clientID + "erhalten:" + received);

        ServerParser.getStringIntoServerParser(received, clientID);
        log.info("[Server] Habe die Nachricht an den Serverparser übergeben....");


      } catch (IOException e) {
        e.printStackTrace();
        //System.out.println("No inputconnection to client: " + clientID);
        log.info("No inputconnection to client: " + clientID);
      }
      if (received != null) {

      }
    }
  }

  public void sendMessageToClient(String message) {
    String messageToClient = message;
    try {

      out.writeUTF(messageToClient);
      //System.out.println("[Server] Nachricht an Client " + clientID + " geschickt.");
      log.info("[Server] Nachricht an Client " + clientID + " geschickt.");
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
