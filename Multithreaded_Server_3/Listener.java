import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;


// Server class
public class Listener extends Thread {

  final List<ClientHandler> Clients;

  public ServerSocket ss;
  int clientNumberId = 0;

  public Listener(List<ClientHandler> listOfClients) {
    this.Clients = listOfClients;
    System.out.println("Listener Start");
  }

  // Start Serversocket, accept and wait for new clients, create new ClientHandler and start client-Thread
  @Override
  public void run() {
    // server is listening on port 5056
    try {
      ServerSocket ss = new ServerSocket(5056);
      // running infinite loop for getting
      // client request
      while (true) {
        Socket s = null;
        try {
          // socket object to receive incoming client requests
          s = ss.accept();

          System.out.println("A new client is connected : " + s);

          // obtaining input and out streams
          DataInputStream dis = new DataInputStream(s.getInputStream());
          DataOutputStream dos = new DataOutputStream(s.getOutputStream());

          System.out.println("Assigning new thread for this client");

          // create a new thread object
          ClientHandler t = new ClientHandler(s, dis, dos);
          ClientHandlerHolder.register(t);
          // Server.gameInfo.registerBy(clientNumberId);

          // Invoking the start() method
          t.start();

          this.Clients.add(t);
          clientNumberId++;
          System.out.println(Clients);
        }
        catch (Exception e){
          s.close();
          e.printStackTrace();
        }
      }
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  public List<ClientHandler> getClients(){
    return Clients;
  }





}
