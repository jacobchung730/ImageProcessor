package controller.commands;

import controller.IImageCommand;
import model.CheckerboardImage;
import model.ILayeredImageModel;

/**
 * Represents the command to load a checkerboard image into the model.
 */
public class LoadCheckerboard implements IImageCommand {

  private final int width;
  private final int height;
  private final int tileSize;


  /**
   * Creates a new LoadCheckerBoard.
   * @param width checkerboard width
   * @param height checkerboard height
   * @param tileSize the size of each tile in pixels
   */
  public LoadCheckerboard(String tileSize, String width, String height)
      throws IllegalArgumentException {
    if (tileSize == null || width == null || height == null) {
      throw new IllegalArgumentException("parameters cannot be null");
    }
    try {
      this.tileSize = Integer.parseInt(tileSize);
      this.width = Integer.parseInt(width);
      this.height = Integer.parseInt(height);

    }
    catch (NumberFormatException e) {
      throw new IllegalArgumentException("invalid number");
    }
  }

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    m.loadImage(new CheckerboardImage(this.tileSize, this.width, this.height).createCheckerboard());

  }


}
