package gui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ClientGui {
  // Client client;
  // Hand clientHand;
  // Player playerTurn;
  // Bag bag;
  //Tile tile;
  TileView tView;
  @FXML private HBox topHand;


  public ClientGui(){
  }

  /**
   * For displaying Tiles on Player hand.
   * @param hand
   */
  void setHand(Hand hand){
    for (int i = 0; i < hand.getTiles().size(); i++){ //kein foreach wegen equals
      Tile tile = hand.getTiles().get(i);
    Image tileImage = tView.getImage(tile);
    tView.createTile(tileImage);
    }
  }

  void placeTiles(){

  }


}
