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


//        server.sendMessage(1, "Test");

        System.out.println("[Server] Test, Aufnahme von bis zu 4 Clients...");

//        for (int i = 0; i < 3; i++) {
//            server.registerClient();
//
//        }

        //server.sendBroadcastMessage(" Hallo du! ");
        //server.registerClient();
        ///server.sendBroadcastMessage("Hallo ihr zwei");
        //server.registerClient();
        //server.sendBroadcastMessage("Hallo ihr drei");
        //server.sendBroadcastMessage("Tschüsslidü");

        server.registerClient();

        server.sendMessage(1, "Hallo Nummer 1");
        server.sendMessage(2, "Hallo Numemr 2");
    }
}
