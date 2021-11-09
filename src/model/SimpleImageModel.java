package model;

import model.filter.IFilter;
import java.util.HashMap;
import java.util.Map;


/**
 * Represents a model for the image processor. This model has methods that can add, remove,
 * replace, and return Images from the map of images.
 */
public class SimpleImageModel implements ISimpleImageModel {

  private final Map<String, IImage> images;


  /**
   * Constructs a new {@code Model} object. this.images is initialized to a new HashMap.
   */
  public SimpleImageModel() {
    this.images = new HashMap<>();
  }

  @Override
  public void addImage(String id, IImage image) throws IllegalArgumentException {
    if (id == null || image == null) {
      throw new IllegalArgumentException("argument cannot be null!");
    }
    this.images.put(id, image);
  }

  @Override
  public void removeImage(String id) throws IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("argument cannot be null!");
    }

    if (!this.images.containsKey(id)) {
      throw new IllegalArgumentException("Image " + id + " does not exist.");
    }

    this.images.remove(id);
  }

  @Override
  public void replaceImage(String id, IImage image) throws IllegalArgumentException {
    if (id == null || image == null) {
      throw new IllegalArgumentException("argument cannot be null!");
    }

    if (!this.images.containsKey(id)) {
      throw new IllegalArgumentException("Image " + id + " does not exist.");
    }
    this.images.replace(id, image);
  }

  @Override
  public IImage getImage(String id) throws IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("id cannot be null!");
    }

    if (!this.images.containsKey(id)) {
      throw new IllegalArgumentException("Image " + id + " does not exist.");
    }

    return this.images.get(id);
  }

  @Override
  public IImage applyFilter(String id, IFilter filter) throws IllegalArgumentException {

    if (id == null || filter == null) {
      throw new IllegalArgumentException("arguments cannot be null!");
    }

    if (!this.images.containsKey(id)) {
      throw new IllegalArgumentException("Image " + id + " does not exist.");
    }

    return filter.applyFilter(this.images.get(id));
  }

}
