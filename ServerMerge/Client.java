import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

  private String ip;
  private final int PORT = 48410;
  Thread listeningThread;
  static DataInputStream in;
  static DataOutputStream out;
  Socket socket;

  public Client(String ip){
    try {
      this.socket = new Socket(ip, PORT);

      this.in = new DataInputStream(socket.getInputStream());
      this.out = new DataOutputStream(socket.getOutputStream());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void sendMessageToServer(String message){
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
            if(!socket.isConnected()) {
              running = false;
            }

            DataInputStream in = new DataInputStream(socket.getInputStream());
            String msg = in.readUTF();

            System.out.println("[Client] ID:" + getId() + " Imcoming message from Server: " + msg );

            ClientParser.parseForView(msg);

//              //an Parser weitergeben...
//            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
    listeningThread.start();
  }
}
