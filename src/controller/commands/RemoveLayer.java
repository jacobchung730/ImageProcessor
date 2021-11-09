package controller.commands;

import controller.IImageCommand;
import model.ILayeredImageModel;

/**
 * Represnts the command to remove a layer from the model.
 */
public class RemoveLayer implements IImageCommand {

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    m.removeLayer();

  }

}
