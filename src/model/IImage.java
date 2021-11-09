package model;

import java.util.List;

/**
 * Represents an image. An IImage is represented by a list of list of pixel and supports pixels
 * of different color ranges.
 */
public interface IImage {

  /**
   * Gets the grid of pixels that makes up the image.
   * @return The list of pixels
   */
  List<List<Pixel>> getGrid();

  /**
   * Gets the max color value for the image.
   * @return the max value
   */
  int getMaxValue();

  /**
   * Gets the width of this image.
   * @return width of this image
   */
  int getWidth();

  /**
   * Gets the height of this image in pixels.
   * @return the height
   */
  int getHeight();

  /**
   * Creates a duplicate of this image.
   */
  IImage duplicateImage();

}
