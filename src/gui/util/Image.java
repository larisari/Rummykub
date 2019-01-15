package gui.util;

import java.io.InputStream;

public class Image extends javafx.scene.image.Image {

  String url;

  public Image(String url) {
    super(url);
    this.url = url;
  }


  public String getURL() {
    return url;
  }

}
