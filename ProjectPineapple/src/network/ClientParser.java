package network;

public class ClientParser {

  private static String recievedMessageFromServer;
  private static String sendMessage;

  public ClientParser() {
  }

  public static void getStringIntoClientParser(String receivedMessageFromServer) {
    receivedMessageFromServer = recievedMessageFromServer;
  }

  public static void getCurrentConnectedClients() {
    Client.sendMessageToServer("getCurrentConnectedClients");

  }


//  public static String parseToString(List<ImageView> tiles){
//    String selectedT = "comb:";
//    for (int i = 0; i < tiles.size(); i++){
//      ImageView iView = tiles.get(i);
//      Image tile = iView.getImage();
//      String url = tile.getUrl();
//      String [] urlArray = url.split("/");
//      String color = urlArray[urlArray.length-2];
//      String [] numberArray = urlArray[urlArray.length-1].split("[.]");
//      String number = numberArray[0];
//      selectedT += "tile." + color + "/" + number;
//      if (i != tiles.size()-1){
//        selectedT += ",";
//      }
//    }
//    return selectedT;
//  }

//  public void sendMessageToServer(){
//    Client.sendMessageToServer(parseToString(List<ImageView> tiles));
//  }


  public static void parseForView(String message) {

  }

}
