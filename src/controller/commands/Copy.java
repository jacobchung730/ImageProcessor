package controller.commands;

import controller.IImageCommand;
import model.ILayeredImageModel;

/**
 * Represents the command to make a copy of a layer.
 */
public class Copy implements IImageCommand {

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    m.createLayerCopy();

  }

}
