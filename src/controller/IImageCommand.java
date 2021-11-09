package controller;

import model.ILayeredImageModel;

/**
 * Interface for all the image commands that the image processor supports. This interface has the
 * go method which apply the corresponding command class to the given model.
 */
public interface IImageCommand {

  /**
   * Executes the command on the given model.
   * @param m the target model
   * @throws IllegalArgumentException throws if model is null
   */
  void applyCommand(ILayeredImageModel m) throws IllegalArgumentException;

}
