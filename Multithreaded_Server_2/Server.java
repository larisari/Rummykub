import java.util.*;

class Server
{
  public static void main(String[] args)
  {
    try
    {
      List<ClientHandler> listOfClients = new ArrayList<ClientHandler>();

      Game game = new Game(listOfClients);
      game.start();
      Listener listener = new Listener(listOfClients);
      listener.start();

      while (game.isAlive()) {

      }

    }
    catch (Exception e)
    {
      e.printStackTrace ();
    }
  }

}
