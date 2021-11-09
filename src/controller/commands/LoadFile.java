package controller.commands;

import controller.IImageCommand;
import controller.ImageUtil;
import model.ILayeredImageModel;
import model.Image;

/**
 * Command class for loading an image into the model from the controller.
 */
public class LoadFile implements IImageCommand {

  private final String path;


  /**
   * Constructs a LoadFile object to load an image.
   * @param path the path of the image file
   * @throws IllegalArgumentException throws if the path string is null
   */
  public LoadFile(String path) throws IllegalArgumentException {

    if (path == null) {
      throw new IllegalArgumentException("path string cannot be null");
    }

    this.path = path;
  }

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    m.loadImage(new Image(ImageUtil.readFile(path)));
  }


}
