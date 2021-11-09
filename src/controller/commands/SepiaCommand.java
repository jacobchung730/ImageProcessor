package controller.commands;

import controller.IImageCommand;
import model.ILayeredImageModel;
import model.filter.Sepia;

/**
 * Represents the command to apply the sepia filter to an image.
 */
public class SepiaCommand implements IImageCommand {

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    m.applyFilter(new Sepia());
  }

}
