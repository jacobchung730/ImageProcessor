package controller.commands;

import controller.IImageCommand;
import model.ILayeredImageModel;

/**
 * Represents the command to move the current layer to a new layer.
 */
public class Current implements IImageCommand {

  private final String name;

  /**
   * Creates a new Current object to set a new current layer.
   * @param name the name of the new current layer
   */
  public Current(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null");
    }

    this.name = name;
  }

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    m.goToLayer(this.name);
  }

}
