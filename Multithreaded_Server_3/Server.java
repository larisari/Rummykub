import java.util.*;

class Server {
  //static final GIGameInfo gameInfo;

  private final Listener listener;

  public Server() {
    //gameInfo = GIFactory.make();

    List<ClientHandler> listOfClients = new ArrayList<ClientHandler>();

    listener = new Listener(listOfClients);
    listener.start();

  }

  public void start() {
    //gameInfo.start();
  }

}
