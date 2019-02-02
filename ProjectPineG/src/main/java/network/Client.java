package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

/** Contains all relevant functions a client needs to communicate with a server. */
public class Client extends Thread {

  private static DataInputStream in;
  private static DataOutputStream out;
  private static Logger log = Logger.getLogger(Client.class.getName());
  private final int PORT = 48410;
  private Thread listeningThread;
  private Socket socket;

  /**
   * Constructor to generate a Client, which connects to the Server.
   *
   * @param ip address of the server, a client wants to connect to.
   */
  public Client(String ip) throws IOException {
    this.socket = new Socket(ip, PORT);
    in = new DataInputStream(socket.getInputStream());
    out = new DataOutputStream(socket.getOutputStream());
    log.info("[Client] wurde erstellt. In-/Outputstreams geöffnet.");
    this.startListening();
  }

  /**
   * Sends a message to the server.
   *
   * @param message message to send to the server.
   */
  static void sendMessageToServer(String message) {

    try {
      out.writeUTF(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Opens a Thread, which listens to incoming messages from the Server and hands over to the Client
   * Parser.
   */
  private void startListening() {
    listeningThread =
        new Thread(
            new Runnable() {
              boolean running = true;

              @Override
              public void run() {
                while (running) {
                  try {

                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    String msg = in.readUTF();

                    log.info("[Client] Incoming message from Server: " + msg);
                    ClientParser.parseForController(msg);

                  } catch (IOException e) {
                    log.info("[Client] Habe Verbindung zum Server verloren....");
                    running = false;
                    ClientParser.parseForController("Restart");
                  }
                }
              }
            });
    listeningThread.start();
  }
}
