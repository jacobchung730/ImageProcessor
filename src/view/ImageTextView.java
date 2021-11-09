package view;

import java.io.IOException;

/**
 * Represents a textual view for the image processor. The renderMessage methods allows the
 * given message to be appended to the view.
 */
public class ImageTextView implements IImageTextView {

  private final Appendable ap;

  /**
   * Constructs a {@code ImageTextView} object that takes in a model and an appendable.
   * @param ap the appendable that will have messages appended to it
   * @throws IllegalArgumentException if ap is null
   */
  public ImageTextView(Appendable ap) throws IllegalArgumentException {
    if (ap == null) {
      throw new IllegalArgumentException("ap cannot be null");
    }

    this.ap = ap;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    ap.append(message);
  }
}
