package view;

import java.awt.image.BufferedImage;

/**
 * Represents a GUI view for an image processor. The GUI displayes the current visible image
 * and allows access to all functionality via some form of menu or buttons.
 */
public interface IInteractiveView {

  /**
   * Adds a layer to the list of layers displayed.
   * @param name the name of the layer to add
   */
  void addLayer(String name);

  /**
   * Removes the layer from the list of layers being displayed.
   * @param name the name of the layer to remove
   */
  void removeLayer(String name);

  /**
   * Sets the image currently being displayed.
   * @param image the image to display
   */
  void setDisplayImage(BufferedImage image);

  /**
   * Adds the given listener to this list of listeners.
   * @param l an IViewListener
   */
  void addListener(IViewListener l);

  /**
   * Displays the given message somewhere in the view.
   * @param message the string message to be displayed
   */
  void displayMessage(String message);

  /**
   * Sets the current layer name that should be displayed.
   * @param s the current layer name
   */
  void setCurrentLayerName(String s);




}
