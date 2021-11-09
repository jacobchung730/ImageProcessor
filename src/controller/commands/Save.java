package controller.commands;

import controller.IImageCommand;
import model.ExportType;
import model.ILayeredImageModel;
import controller.ImageUtil;


/**
 * Saves the image as a single file containing the visible image from the layers.
 */
public class Save implements IImageCommand {

  private final String name;
  private final String fileType;


  /**
   * Creates a new Save object to save an image.
   * @param name the name of the image to be saved
   * @param fileType the file type of the image to be saved
   * @throws IllegalArgumentException if name or fileType is null
   */
  public Save(String name, String fileType) throws IllegalArgumentException {
    if (name == null || fileType == null) {
      throw new IllegalArgumentException("parameter cannot be null.");
    }
    this.name = name;
    this.fileType = fileType;
  }

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    ImageUtil u = new ImageUtil();
    u.exportImage(name, ExportType.getEnum(this.fileType), m.getVisibleImage());


  }

}
