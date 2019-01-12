package gui;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import gui.TileView;

public class ClientGui {

  // Client client;
  // Hand clientHand;
  // Player playerTurn;
  // Bag bag;
  //gui.Tile tile;
  TileView tView;
  GuiController controller;
  @FXML
  private HBox topHand;

  //3-4 Player.
  //ab 3 Spieler: Spiel starten möglich.
  //Aufgeben weg

  public ClientGui() {
  }


  void placeTiles() {
    List<ImageView> selectedTiles = controller.getSelectedTiles();

  }


}
