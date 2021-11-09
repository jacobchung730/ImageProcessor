package controller.commands;

import controller.IImageCommand;
import controller.ImageUtil;
import java.util.Map;
import model.IImage;
import model.ILayeredImageModel;


/**
 * Command class for loading a project into the model from the controller.
 */
public class LoadProject implements IImageCommand {

  private final String folderString;


  /**
   * Constructs a LoadProject object to load an image.
   * @param folderString the path for the project that will be loaded
   * @throws IllegalArgumentException if the folder string path is null
   */
  public LoadProject(String folderString) throws IllegalArgumentException {

    if (folderString == null) {
      throw new IllegalArgumentException("folder string cannot be null");
    }

    this.folderString = folderString;
  }

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }

    Map<String, IImage> mapForNewModel = ImageUtil.readLayeredImage(this.folderString);

    m.loadProject(mapForNewModel);

  }

}

