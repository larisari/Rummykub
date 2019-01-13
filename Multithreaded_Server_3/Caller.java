import java.util.List;

class Caller {

  private Caller() {}

  static void callEveryOne() {
    List<ClientHandler> clients = ClientHandlerHolder.getRegisteredClients();
    for (ClientHandler handler : clients) {
     handler.callClientWith("IT DOES WORK NOW.");
    }
  }

}
