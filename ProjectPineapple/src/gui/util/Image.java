package gui.util;


public class Image extends javafx.scene.image.Image {

  String url;
  String id;

  public Image(String url) {
    super(url);
    this.url = url;
  }

  public Image(String url, String id) {
    super(url);
    this.id = id;
  }


  public String getURL() {
    return url;
  }


  public String getId() {
    return id;
  }
}