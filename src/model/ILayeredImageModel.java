package model;

import java.util.Map;
import model.filter.IFilter;

/**
 * Represents the model for a layered image. A layered image is an image made up of layers that
 * each hold an image(s) and whose visibilty can be toggled on or off.
 * The model can add/remove layers and images and apply filters to each layer image.
 */
public interface ILayeredImageModel {

  /**
   * Creates a new empty layer in the model. Each layer must have a unique name.
   * @param name the name of the layer.
   * @throws IllegalArgumentException is the name is null or already exists in the model
   */
  void createLayer(String name) throws IllegalArgumentException;

  /**
   * Creates a new layer that is a copy of the current layer. Also sets the current layer to be
   * the new layer that was copied.
   * @throws IllegalArgumentException if the model has no layers
   */
  void createLayerCopy() throws IllegalArgumentException;

  /**
   * Removes the current layer. If it is in the middle, then layers will shift accordingly.
   * @throws IllegalArgumentException if the model has no layers
   */
  void removeLayer() throws IllegalArgumentException;

  /**
   * Goes to the layer at the given string name.
   * @param name the name of the layer
   * @throws IllegalArgumentException if the name is null or does match a layer
   */
  void goToLayer(String name) throws IllegalArgumentException;

  /**
   * Makes the current layer invisible.
   * @throws IllegalArgumentException if the model has no layers
   */
  void makeLayerInvisible() throws IllegalArgumentException;

  /**
   * Makes the current layer visible.
   * @throws IllegalArgumentException if there are no layers
   */
  void makeLayerVisible() throws IllegalArgumentException;

  /**
   * Loads the given image to the current layer. If no images exist yet, this image will provide the
   * base dimensions that future images will need to be.
   * If the layer already has an image, that image will be replaced by the new image being loaded.
   * @param image the given image that will be loaded to current layer
   * @throws IllegalArgumentException if the image is null
   */
  void loadImage(IImage image) throws IllegalArgumentException;

  /**
   * Loads the layers and images. This method is called after the load-project command is called.
   * This method will only work if the current layer is null, meaning that there are currently
   * no layers and images in this model.
   * @param multipleImages A Map of the string name to the image
   * @throws IllegalArgumentException throws if multipleImages is null or if trying to loadProject
   *                                  when there are either layers or images in this model
   */
  void loadProject(Map<String, IImage> multipleImages) throws IllegalArgumentException;

  /**
   * Removes the image at the current layer. The layer's value will be reset to null.
   * @throws IllegalArgumentException if there are no layers or there is no image at the current
   *                                  layer
   */
  void removeImage() throws IllegalArgumentException;

  /**
   * Applies the given filter to the image at the current layer.
   * @param filter the filter that will be applied to the image
   * @throws IllegalArgumentException if the filter is null, there are no layers, or the layer has
   *                                  no image
   */
  void applyFilter(IFilter filter) throws IllegalArgumentException;

  /**
   * Gets the image at the given string name.
   * @return the Image at the given string name.
   * @throws IllegalArgumentException if the name is null or does not exist in the model
   */
  IImage getImage(String name) throws IllegalArgumentException;

  /**
   * Returns the topmost visible image in the multilayered image.
   * @return topmost visible image.
   * @throws IllegalArgumentException if there is no visible image
   */
  IImage getVisibleImage() throws IllegalArgumentException;

  /**
   * Returns the layer contents (map of string to images) from this model.
   * @return the contents of each layer
   */
  Map<String, IImage> getLayerContents();

  /**
   * Returns the current layer as a string.
   * @return the name of the current layer
   */
  String getCurrentLayer();

  /**
   * Returns the layer visibilities (map of string to boolean) from this model.
   * @return the visibilities of each layer.
   */
  Map<String, Boolean> getLayerVisibility();



}
