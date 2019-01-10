package de.netzwerk.client;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class MyClient implements Serializable {
  public static void main(String[] args) {
    //if ( neues Spiel gehostet ...
      MyClient client = new MyClient("localhost", 48410); // Hostaddresse muss geändert werden, localhost ist immer eigener Rechner
      client.sendMessage("Client verbunden");

      // Spielbeitritt
      //else
      //MyClient remoteClient = new MyClient(server.getLocalHost(), 48410);
  }


   private SocketAddress address; // auf dieser Addresse verbindet sich später der Client

  public MyClient(String hostname, int port){
      address = new InetSocketAddress(hostname, port); //hostname entspricht dem Rechner auf dem der Server läuft
  }



  //diese Message wird nachher an den Server gesendet
  public void sendMessage(String msg) {


      try{
          System.out.println("[Client] Verbinde zu Server....");
           Socket socket = new Socket();
          socket.connect(address,5000);
          System.out.println("[Client] Verbunden!");


          //nun müssen wir Daten schreiben

          System.out.println("[Client] Sende Nachricht....");
          PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
          pw.println(msg);
          pw.flush(); // Daten dann abrauschen durch die Leitung
          System.out.println("[Client] Nachricht gesendet!");


          //Verbindung wieder schließen
          //pw.close();
          //socket.close();

      }catch (Exception e) {
          e.printStackTrace();
      }
  }
}
