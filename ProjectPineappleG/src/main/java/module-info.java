module ProjectPineappleG {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.logging;
  requires org.junit.jupiter.api;

  opens gui to javafx.fxml;
  exports gui;
}