package de.netzwerk.NetzwerkTests;

import de.netzwerk.client.MyClient;
import de.netzwerk.server.MyServer;

public class Main {


    //Only for Testing....
    public static void main(String[] args) {

        MyServer server = new MyServer(48410, true);
        System.out.println("[Server] ID des Clients lautet:" + server.getClients().get(0).getId());
        System.out.println(
                "[Server] Client Connected? " + server.getClients().get(0).isConnected());
        System.out.println("[Client] Anfrage: ist Server erreichbar? " + server.getClients().get(0).isServerReachable());

        System.out.println("[Server] Test, Aufnahme von bis zu 4 Clients...");

        for (int i = 0; i < 5; i++) {
            server.registerClient();
        }
    }
}
