import java.io.IOException;
import java.net.ServerSocket;

public class ServerRummikub {


  int clientCounter = 0;

  public ServerRummikub(){
    int PORT = 48410;

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      while (true){
        new ServerThreadsRummikub(serverSocket.accept()).start();
      }
    } catch (IOException e) {
      System.out.println("It was not possible to create a server...");
    }
  }






}
