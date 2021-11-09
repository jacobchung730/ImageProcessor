package controller.commands;

import controller.IImageCommand;
import model.ILayeredImageModel;
import model.filter.Sharpen;

/**
 * Represents the commnad to apply the sharpen filter to an Image.
 */
public class SharpenCommand implements IImageCommand {

  @Override
  public void applyCommand(ILayeredImageModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }
    m.applyFilter(new Sharpen());
  }

}
