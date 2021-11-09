package model.filter;

import model.IImage;

/**
 * Represents a filter than can be applied to an image.
 */
public interface IFilter {


  /**
   * Applies this filter to the given image.
   * @param image the image the filter is applied to
   * @return a new image
   */
  IImage applyFilter(IImage image);

}
