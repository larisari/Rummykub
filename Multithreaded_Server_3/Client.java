import java.io.*;
import java.net.*;
import java.util.Scanner;

// Client class
public class Client {

  private DataInputStream in;
  private DataOutputStream out;
  private Socket client;
  private int PORT = 5056;
  String recievedMessage;

  public Client(){
  }

  public synchronized boolean connectToServer(String ip){
    try {
      this.client = new Socket(ip, PORT);
      this.in = new DataInputStream(client.getInputStream());
      this.out = new DataOutputStream(client.getOutputStream());

      // the following loop performs the exchange of
      // information between client and client handler
      // it listens to new messages from the clientHandler

      while (true) {
        String recievedMessageFromClientHandler = in.readUTF();
        // clientParser.parse(recievedMessageFromClientHandler);

      }

    }catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public synchronized void sendMessageToServer(String message){
    try {
      out.writeUTF(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.notifyAll();
  }

  public synchronized void closeClient(){
    System.out.println("Closing this connection : " + client);
    try {
      client.close();
      in.close();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Connection closed");
    notifyAll();
  }

}
