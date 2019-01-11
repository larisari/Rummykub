import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientRummikub {


  int PORT = 48410;

  Socket client;
  ObjectOutputStream out;
  ObjectInputStream in;

  public ClientRummikub(String ip){
    try {
      client = new Socket(ip, PORT);
      in = new ObjectInputStream(client.getInputStream());
      out = new ObjectOutputStream(client.getOutputStream());
    } catch (UnknownHostException e) {
      System.out.println("Making a connection to server failed. Unknown host.");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("Making a connection to server failed.");
      e.printStackTrace();
    }
  }

  public void sendObject(Object o){
    try {
      out.writeObject(o);
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void recieveObject(){

  }







}
