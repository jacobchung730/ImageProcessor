package controller.commands;

import controller.IImageCommand;
import model.ILayeredImageModel;
import model.filter.Greyscale;

/**
 * Represents the command to apply the greyscale filter on an image.
 */
public class GreyscaleCommand implements IImageCommand {

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    m.applyFilter(new Greyscale());
  }

}
