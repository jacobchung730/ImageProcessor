package controller.commands;

import controller.IImageCommand;
import model.ILayeredImageModel;
import model.filter.Blur;

/**
 * Represent the command to apply the blur filter on an image.
 */
public class BlurCommand implements IImageCommand {

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    m.applyFilter(new Blur());
  }

}
