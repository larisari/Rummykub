import java.util.Scanner;

public class TestClient {

  private static Client client;

  public static void main(String[] args) {
    String ip = "localhost";
    client = new Client();
    System.out.println("SERVER CREATED");
    client.connectToServer(ip);

    System.out.println("SERVER CONNECTED");
    Scanner sc = new Scanner(System.in);
    String message = sc.nextLine();
    client.sendMessageToServer(message);
  }

}
