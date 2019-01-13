import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class ClientHandler extends Thread
{
  DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
  DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
  final DataInputStream in;
  final DataOutputStream out;
  final Socket s;


  // Constructor
  public ClientHandler(Socket s, DataInputStream in, DataOutputStream out)
  {
    this.s = s;
    this.in = in;
    this.out = out;
  }

  @Override
  public void run()
  {
    String received;
    String toreturn;
    while (true)
    {
      try {

        // Ask user what he wants
        out.writeUTF("What do you want?[Date | Time]..\n"+
            "Type Exit to terminate connection.");

        // receive the answer from client
        received = in.readUTF();

        if(received.equals("Exit"))
        {
          System.out.println("Client " + this.s + " sends exit...");
          System.out.println("Closing this connection.");
          this.s.close();
          System.out.println("Connection closed");
          break;
        }

        // creating Date object
        Date date = new Date();

        // write on output stream based on the
        // answer from the client
        switch (received) {
          case "Test":
            Caller.callEveryOne();
            break;
          case "Date" :
            toreturn = fordate.format(date);
            out.writeUTF(toreturn);
            break;

          case "Time" :
            toreturn = fortime.format(date);
            out.writeUTF(toreturn);
            break;

          default:
            out.writeUTF("Invalid input");
            break;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    try
    {
      // closing resources
      this.in.close();
      this.out.close();

    }catch(IOException e){
      e.printStackTrace();
    }
    System.out.println("Thread ending...");
  }

  public void callClientWith(String message) {
    try {
      out.writeUTF(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}



/*
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class ClientHandler extends Thread {

  private DataInputStream in;
  private DataOutputStream out;
  private Socket s;
  private int clientId;

  // Constructor
  public ClientHandler(Socket s, DataInputStream in, DataOutputStream out, int clientId)
  {
    this.s = s;
    this.in = in;
    this.out = out;
    this.clientId = clientId;
  }

  @Override
  public void run() {
    String received;
    String toreturn;

    while (true)
    {
      try {

        out.writeUTF("Hallo Willkommen im Test.");

        // receive any input from client
        received = in.readUTF();

        if(received.equals("Exit"))
        {
          System.out.println("Client " + this.s + " sends exit...");
          System.out.println("Closing this connection.");
          this.s.close();
          System.out.println("Connection closed");
          break;
        }

        // write on output stream based on the
        // answer from the client
        // "getStack"
        // "play*comb:tile.blue/2,..."
        //String[] command = Parser.parseCommand(received);

        // command muss mind. length 1 haben
        //switch (command[0]) {
        switch (received) {
          case "Test":
            Caller.callEveryOne();
            break;
          case "callEveryOne":
            out.writeUTF("CALLED");
            break;
          case "getStack" :
            /*
            Optional<Combination> combinationOptional = Server.gameInfo.drawBy(clientId);

            if (combinationOptional.isPresent()) {
              String result = Parser.parse(combinationOptional.get());
              out.writeUTF(result);
            } else {
              // something went wrong !!! handle accordingly !!!
            }

            break;

          case "play":
            //  Optional<Boolean> play(List<Tile> combination, String id);
            /*
            Combination combination = Parser.parseCombination(command[1]);
            Optional<Boolean> optionalBoolean = Server.gameInfo.play(combination, clientId);

            if (optionalBoolean.isPresent()) {
              String result = Parser.parse(optionalBoolean.get());
              out.writeUTF(result);
            } else {
              // something went wrong handle differently.
            }
            out.writeUTF("play");
            break;


          case "Time" :
            //toreturn = fortime.format(date);
            out.writeUTF("ZEIT");
            break;

          default:
            out.writeUTF("Invalid input");
            break;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  public void closeClientHandler(){
    try { // closing resources
      System.out.println("Closing this connection.");
      this.s.close();
      System.out.println("Connection closed");
      this.in.close();
      this.out.close();

    } catch(IOException e){
      e.printStackTrace();
    }
    System.out.println("Thread ending...");
  }

  public void callClientWith(String message) {
    try {
      out.writeUTF(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getClientId(){
    return this.clientId;
  }


}
*/