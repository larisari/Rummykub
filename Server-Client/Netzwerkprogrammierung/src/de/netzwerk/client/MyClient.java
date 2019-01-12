package de.netzwerk.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class MyClient implements Serializable {

    // Attribute
    private SocketAddress address; // auf dieser Addresse verbindet sich später der Client
  private Socket socket;
  private int port;
  private int id;
    private Thread listeningThread;
    private DataOutputStream out;
    private DataInputStream in;

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

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
        } catch (IOException e) {
            System.out.println("Verbindung zum Server fehlgeschlagen!" + e.getMessage());
        }
    }

    public void sendMessage(String msg) {
        try {
            this.out = new DataOutputStream(socket.getOutputStream());
            this.out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAnswer() {
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
