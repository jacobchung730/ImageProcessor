package controller.commands;

import controller.IImageCommand;
import model.ILayeredImageModel;

/**
 * Represents the command to set a layers visiblity to invisible.
 */
public class Invisible implements IImageCommand {

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    m.makeLayerInvisible();

  }

}
