package gui.util;

/**
 * For creating Images with custom url and id. Constructor with id is only used for joker tile.
 */
public class Image extends javafx.scene.image.Image {

  String url;
  String id;

  /**
   * Creates an image with given url.
   *
   * @param url - path to image.
   */
  public Image(String url) {
    super(url);
    this.url = url;
  }

  /**
   * Creates an image with given url and id. For identifying joker.
   *
   * @param url - path to image.
   * @param id - joker id.
   */
  public Image(String url, String id) {
    super(url);
    this.url = url;
    this.id = id;
  }

  /**
   * Returns the url of an image.
   *
   * @return url of an image.
   */
  public String getURL() {
    return url;
  }

  /**
   * Returns id of an image.
   *
   * @return id of an image.
   */
  public String getId() {
    return id;
  }
}