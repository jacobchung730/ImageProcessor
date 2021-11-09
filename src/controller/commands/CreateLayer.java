package controller.commands;

import controller.IImageCommand;
import model.ILayeredImageModel;

/**
 * Represents the command to create a new layer.
 */
public class CreateLayer implements IImageCommand {

  private final String name;

  /**
   * Creates a new Creatlayer object.
   * @param name the name of the layer to be creates
   */
  public CreateLayer(String name) throws IllegalArgumentException {
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
    m.createLayer(this.name);
  }

}
