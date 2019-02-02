package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

/** Thread, which manages incoming messages of all clients. */
public class ServerClientCommunication extends Thread {

  private static Logger log = Logger.getLogger(ServerClientCommunication.class.getName());
  private final DataInputStream in;
  private final DataOutputStream out;
  private final Socket s;
  private final int clientID;

  /**
   * Thread object to a client.
   *
   * @param s socket connection to one client.
   * @param in DataInputStream, to receive messages from this client.
   * @param out DataOutputstream to write messages to this client.
   * @param clientID ID of this client.
   */
  ServerClientCommunication(Socket s, DataInputStream in, DataOutputStream out, int clientID) {
    this.in = in;
    this.out = out;
    this.s = s;
    this.clientID = clientID;
  }

  /**
   * Getter for Socket.
   *
   * @return the socket.
   */
  public Socket getS() {
    return s;
  }

  /**
   * Getter for DatainputStream.
   *
   * @return Inputstream.
   */
  public DataInputStream getIn() {
    return in;
  }

  /**
   * Getter for DataOutputStream.
   *
   * @return Outputstream.
   */
  public DataOutputStream getOut() {
    return out;
  }
  /**
   * Getter of client ID.
   *
   * @return the ID of this client.
   */
  int getClientID() {
    return this.clientID;
  }

  /** starts Thread, which listens for incoming messages of all connected clients. */
  @Override
  public void run() {

    while (!s.isClosed()) {
      // safe incoming message in this variable.
      String received;

      try {
        received = in.readUTF();

        log.info("[Server] Nachricht von " + clientID + "erhalten:" + received);

        ServerParser.getStringIntoServerParser(received, clientID);
        log.info("[Server] Habe die Nachricht an den Serverparser übergeben....");

      } catch (IOException e) {

        log.info("ServerClientCommunication Exception");
        log.info("[Server] Client " + clientID + " hat die Verbindung getrennt");
        disconnectClient();
        log.info("[Server] Habe Client disconnected.");
      }
    }
  }

  /**
   * Server sends message to Client.
   *
   * @param message server-message to client.
   */
  void sendMessageToClient(String message) {
    if (s.isConnected()) {
      try {
        out.writeUTF(message);
        log.info("[Server] Nachricht an Client " + clientID + " geschickt: " + message);
      } catch (SocketException e) {
        log.info(
            "[Server] Übermitteln der Nachricht an "
                + clientID
                + " klappt nicht, da Client bereits die Verbindung getrennt hat.");
      } catch (IOException io) {
        io.printStackTrace();
      }
    }
  }

  /** Closes all streams and socket to a client. */
  private void disconnectClient() {
    try {
      this.in.close();
      log.info("CLient in InputStream geschlossen");
      this.out.close();
      log.info("CLient in OutputStream geschlossen");
      this.s.close();
      log.info("is Client Socket closed?!?  " + s.isClosed());

      ServerListener.isRunning = false;

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
