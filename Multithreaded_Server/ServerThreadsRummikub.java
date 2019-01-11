import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThreadsRummikub extends Thread {
  private Socket client = null;
  ObjectOutputStream out;
  ObjectInputStream in;

  public ServerThreadsRummikub(Socket client) {
    super("ServerRummikub");
    this.client = client;
  }

  public void run(){

      try {
        in = new ObjectInputStream(client.getInputStream());
        out = new ObjectOutputStream(client.getOutputStream());

      } catch (IOException e) {
        e.printStackTrace();
      }
  }


}
