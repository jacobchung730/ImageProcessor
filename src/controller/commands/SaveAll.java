package controller.commands;

import controller.IImageCommand;
import model.ExportType;
import model.ILayeredImageModel;
import controller.ImageUtil;

/**
 * Represents the command to save all layers of the layered image model.
 */
public class SaveAll implements IImageCommand {

  private final String folder;
  private final String fileType;

  /**
   * Creates a new SaveAll.
   * @param folder the folder to save all the images in
   * @param fileType the file type for all the saved images
   */
  public SaveAll(String folder, String fileType) throws IllegalArgumentException {
    if (folder == null || fileType == null) {
      throw new IllegalArgumentException("parameter cannot be null.");
    }

    this.folder = folder;
    this.fileType = fileType;
  }

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    ImageUtil u = new ImageUtil();
    u.exportLayeredImage(m, folder, ExportType.getEnum(this.fileType));


  }

}
