import java.util.ArrayList;
import java.util.List;

public class ClientHandlerHolder {

  private static List<ClientHandler> clients = new ArrayList<>();

  private ClientHandlerHolder() {}

  static void register(ClientHandler handler) {
    clients.add(handler);
  }

  static void unregister(ClientHandler handler) {
    clients.remove(handler);
  }

  static List<ClientHandler> getRegisteredClients() {
    return clients;
  }

}
