package de.netzwerk.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class MyClient implements Serializable {

  // Attribute
  private SocketAddress address;
  private Socket socket;
  private int id;
  private Thread listeningThread;
  private DataOutputStream out;
  private DataInputStream in;
  private ClientParser parser;

  /**
   * Constructor
   *
   * @param hostname (IP-Adresse des Servers)
   * @param port     (Port, mit dem man den Server erreicht)
   */
  public MyClient(String hostname, int port, int id) {
    address = new InetSocketAddress(hostname, port);
    this.id = id;
  }

  public Socket getSocket() {
    return socket;
  }

  public int getId() {
    return id;
  }

  public boolean isListening() {
    return socket.isConnected() && listeningThread != null;
  }

  public boolean isConnected() {
    return socket != null && socket.isConnected();
  }

  public boolean isServerReachable() {
    try {
      Socket temp = new Socket();
      temp.connect(this.address);
      temp.isConnected();
      temp.close();
      return true;

    } catch (IOException e) {
      return false;
    }
  }

  public void login() {
    this.socket = new Socket();
    try {
      System.out.println("Client connecting....");
      socket.connect(this.address);
      System.out.println("Client connected!");
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());

      startListening();
    } catch (IOException e) {
      System.out.println("Verbindung zum Server fehlgeschlagen!" + e.getMessage());
    }
  }

  public void startListening() {
    listeningThread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          try {

            System.out.println(Thread.currentThread() + in.readUTF());
            if (!in.readUTF().isEmpty()) {

              //Parser Funktion rufen...
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });

    listeningThread.start();


  }

  public void sendMessage(String msg) {
    try {
      //if(clientparserListener != null)
      this.out.writeUTF(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

//  public boolean getMessage() {
//    try {
//      while (in.readUTF() == null) {
//        return false;
//      }
//      return true;
//    } catch (IOException e) {
//      e.printStackTrace();
//
//    }
//  }

  public void disconnect() {
    if (!this.socket.isConnected()) {
      return;
    }
    try {
      in.close();
      out.close();
      socket.close();
    } catch (IOException e) {
      e.getMessage();
    }
  }
}
