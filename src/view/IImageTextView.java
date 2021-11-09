package view;

import java.io.IOException;

/**
 * Represents a textual view for the image processor. The renderMessage methods allows the
 * given message to be appended to the view.
 */
public interface IImageTextView {

  /**
   * Displays the view.
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;

}
