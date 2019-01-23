package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class Client extends Thread {

  static DataInputStream in;
  static DataOutputStream out;
  private final int PORT = 48410;
  Thread listeningThread;
  Socket socket;
  private String ip;
  private static Logger log = Logger.getLogger(Client.class.getName());


  /**
   * Client-constructor for Hoster
   */
  public Client() {

    try {
      this.socket = new Socket("127.0.0.1", PORT);

      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
      this.startListening();


      log.info("[Host-Client] wurde erstellt. In-/Outputstreams geöffnet. ");
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

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

  public static void sendMessageToServer(String message) {
    String messageToServer = message;
    try {
      out.writeUTF(messageToServer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void startListening() {
    listeningThread = new Thread(new Runnable() {
      @Override
      public void run() {
        boolean running = true;
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


