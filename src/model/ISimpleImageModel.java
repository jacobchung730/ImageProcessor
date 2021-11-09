package model;

import model.filter.IFilter;

/**
 * Represents the model for this image processing program.
 * The model can hold multiple images for processing, and applies filters to images to
 * process them.
 */
public interface ISimpleImageModel {

  /** adds the given image mapped this this id to this model.
   * @param id the id that the image will be mapped to.
   * @param image the image we want to add.
   * @throws IllegalArgumentException if the id or image are null
   */
  void addImage(String id, IImage image) throws IllegalArgumentException;

  /**
   * Removes the given image from the model.
   * @param id the id of the image
   * @throws IllegalArgumentException if id is null
   */
  void removeImage(String id) throws IllegalArgumentException;

  /**
   * Replaces the image at the given id with the new image.
   * @param id the image to be replaced
   * @param image the new image
   * @throws IllegalArgumentException if id or image are null, or the id does not exist in the model
   */
  void replaceImage(String id, IImage image) throws IllegalArgumentException;

  /**
   * returns the image at the given id.
   * @param id id for the image.
   * @return an image at the id.
   * @throws IllegalArgumentException if the id is null or does not exist in the model
   */
  IImage getImage(String id) throws IllegalArgumentException;

  /**
   * Applies the given filter to the given image.
   * @param id the image
   * @param filter the filter
   * @return the image with the filter applied
   * @throws IllegalArgumentException if the id or filter is null, or the ide does not exist
   *                                  in the model
   */
  IImage applyFilter(String id, IFilter filter) throws IllegalArgumentException;


}
