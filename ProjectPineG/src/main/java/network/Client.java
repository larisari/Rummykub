package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class Client extends Thread {

  static DataInputStream in;
  static DataOutputStream out;
  private static Logger log = Logger.getLogger(Client.class.getName());
  private final int PORT = 48410;
  Thread listeningThread;
  Socket socket;
  private String ip;

  //  /**
  //   * Client-constructor for Hoster
  //   */
  //  public Client() {
  //
  //    try {
  //      this.socket = new Socket("127.0.0.1", PORT);
  //
  //      in = new DataInputStream(socket.getInputStream());
  //      out = new DataOutputStream(socket.getOutputStream());
  //      this.startListening();
  //
  //
  //      log.info("[Host-Client] wurde erstellt. In-/Outputstreams geöffnet. ");
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //  }

  /**
   * Constructor to generate a Client, which connects to the Server.
   *
   * @param ip address of the server, a client wants to connect to.
   */
  public Client(String ip) {
    try {
      this.socket = new Socket(ip, PORT);

      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
      log.info("[Client] wurde erstellt. In-/Outputstreams geöffnet.");
      this.startListening();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sends a message to the server.
   *
   * @param message message to send to the server.
   */
  public static void sendMessageToServer(String message) {
    String messageToServer = message;
    try {
      out.writeUTF(messageToServer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Opens a Thread, which listens to incoming messages from the Server and hands over to the Client
   * Parser.
   */
  public void startListening() {
    listeningThread =
        new Thread(
            new Runnable() {
              boolean running = true;

              @Override
              public void run() {
                while (running) {
                  try {
                    if (socket.isConnected()) {
                      DataInputStream in = new DataInputStream(socket.getInputStream());
                      String msg = in.readUTF();

                      log.info("[Client] Incoming message from Server: " + msg);
                      ClientParser.parseForController(msg);

                    } else {
                      running = false;

                    }
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                }
              }
            });
    listeningThread.start();
  }
}
