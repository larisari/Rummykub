package network;

import gui.Main;
import gui.StartingScreenController;

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

  public int getClientID(){
    return this.clientID;
  }

  /**
   * starts Thread, which listens for incoming messages of all connected clients.
   */
  @Override
  public void run() {

    while (true && s.isConnected()) {
      String received = null;

      try {
        received = in.readUTF();
        //System.out.println("[Server] Nachricht von " + clientID + "erhalten:" + received);
        log.info("[Server] Nachricht von " + clientID + "erhalten:" + received);


        ServerParser.getStringIntoServerParser(received, clientID);
        log.info("[Server] Habe die Nachricht an den Serverparser übergeben....");


      } catch (IOException e) {
        e.printStackTrace();
        log.info("No inputconnection to client: " + clientID);

        Thread.currentThread().interrupt();
        Server.broadcastToAllClients("Restart");
        try{
          ServerListener.ss.close();
        }catch (IOException ex) {
          log.info("[Server] Server kann nicht gekillt werden...");
        }
      }
    }
  }

  /**
   * Server sends message to Client.
   * @param message server-message to client.
   */
  public void sendMessageToClient(String message) {
    String messageToClient = message;
    try {

      out.writeUTF(messageToClient);
      log.info("[Server] Nachricht an Client " + clientID + " geschickt.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Disconnection with a client.
   */
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
