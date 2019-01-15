import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerListener extends Thread {

    static List<ServerClientCommunication> clients;
    int clientID = 0;
    boolean isRunning = true;

    public ServerListener(List<ServerClientCommunication> listOfClients){
      this.clients = listOfClients;
      System.out.println("Listener Start");
    }

    @Override
    public void run(){
      // server is listening on port 48410
      try {
        ServerSocket ss = new ServerSocket(48410);
        // running infinite loop for getting
        // client request

        while (isRunning) {
          Socket s = null;

          try {
            if (clientID < 4) {
              s = ss.accept();

              System.out.println("A new client is connected : " + s);

              DataInputStream in = new DataInputStream(s.getInputStream());
              DataOutputStream out = new DataOutputStream(s.getOutputStream());

              System.out.println("Assigning new thread for this client");
              // create a new thread object
              ServerClientCommunication t = new ServerClientCommunication(s, in, out, clientID);
              // Invoking the start() method
              t.start();

              this.clients.add(t);
              clientID++;
            } else isRunning = false;
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


  public void broadcastToAllClients(String message){
    for (ServerClientCommunication client : clients){
      client.sendMessageToClient(message);
    }
  }

    public static List<ServerClientCommunication> getClients(){
      return clients;
    }

  }
