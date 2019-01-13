import java.util.*;
import java.io.*;



public class Game extends Thread
{
  final List<ClientHandler> Clients;

  public Game( List<ClientHandler> listOfClients )
  {
    this.Clients = listOfClients;
    System.out.println("Game Start");
  }

  @Override
  public void run()
  {
    System.out.println("Game running...");
    Date start = new Date();
    while (true)
    {
      try {
        Thread.sleep(1L * 1000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      long seconds = (new Date().getTime()-start.getTime())/1000;

      if (seconds>5) {
        start = new Date();
        System.out.println("5 Sec");

        for (int i = 0;i<this.Clients.size();i++){
          ClientHandler ch = this.Clients.get(i);
          if (ch.isAlive()) {
            System.out.println(ch);
          }
        }

      }
    }
  }
}
