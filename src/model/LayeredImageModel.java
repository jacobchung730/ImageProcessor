package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import model.filter.IFilter;

/**
 * The model for processing a layered image.
 * The model supports a list of layers that may each contain an IImage.
 */
public class LayeredImageModel implements ILayeredImageModel {

  private final Map<String, Boolean> layerVisibility;
  private final Map<String, IImage> layerContents;
  private String currentLayer;
  private int width;
  private int height;

  /**
   * Constructs a new empty model.
   */
  public LayeredImageModel() {
    this.layerVisibility = new LinkedHashMap<>();
    this.layerContents = new LinkedHashMap<>();
    this.currentLayer = null;
  }

  /**
   * Constructs a new model from the given layers.
   * @param layers the layers of images
   * @throws IllegalArgumentException throws if layers is null
   */
  public LayeredImageModel(Map<String, IImage> layers) throws IllegalArgumentException {
    if (layers == null) {
      throw new IllegalArgumentException("Layers cannot be null.");
    }

    this.layerContents = new LinkedHashMap<>();
    for (Map.Entry<String, IImage> i : layers.entrySet()) {
      this.layerContents.put(i.getKey(), i.getValue());
      this.currentLayer = i.getKey();
    }

    this.layerVisibility = new LinkedHashMap<>();
    for (String s: layers.keySet()) {
      this.layerVisibility.put(s, true);
    }

    this.width = this.layerContents.get(this.currentLayer).getWidth();
    this.height = this.layerContents.get(this.currentLayer).getHeight();
  }

  @Override
  public void createLayer(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    if (this.layerContents.containsKey(name)) {
      throw new IllegalArgumentException("layer with that name already exists");
    }
    this.layerContents.put(name, null);
    this.layerVisibility.put(name, true);
    this.currentLayer = name;
  }

  @Override
  public void createLayerCopy() {
    if (layerContents.size() == 0) {
      throw new IllegalArgumentException("there are no layers");
    }

    IImage copy;
    if (this.layerContents.get(this.currentLayer) != null) {
      copy = this.layerContents.get(this.currentLayer).duplicateImage();
    }
    else {
      copy = null;
    }
    String copyName = this.currentLayer + " copy";
    boolean nameFound = false;

    while (!nameFound) {
      if (this.layerContents.containsKey(copyName)) {
        copyName = copyName + " copy";
      }
      else {
        nameFound = true;
      }
    }
    this.layerContents.put(copyName, copy);
    this.layerVisibility.put(copyName, true);
    this.currentLayer = copyName;
  }

  @Override
  public void removeLayer() throws IllegalArgumentException {
    if (layerContents.size() == 0) {
      throw new IllegalArgumentException("there are no layers");
    }

    //String s = this.currentLayer;
    List<String> layers = new ArrayList<>(getLayerContents().keySet());

    this.layerContents.remove(this.currentLayer);
    this.layerVisibility.remove(this.currentLayer);

    // if removing removed all layers
    if (layerContents.size() == 0) {
      this.currentLayer = null;
    } else {
      this.currentLayer = layers.get(layers.size() - 1);
    }
  }

  @Override
  public void goToLayer(String name) throws IllegalArgumentException {
    if (name == null) {
      this.currentLayer = null;
    }
    else if (!this.layerContents.containsKey(name)) {
      throw new IllegalArgumentException("key does not exist");
    }
    this.currentLayer = name;
  }

  @Override
  public void makeLayerInvisible() throws IllegalArgumentException {
    if (this.currentLayer == null) {
      throw new IllegalArgumentException("image has no layers");
    }
    this.layerVisibility.replace(this.currentLayer, false);
  }

  @Override
  public void makeLayerVisible() throws IllegalArgumentException {
    if (this.currentLayer == null) {
      throw new IllegalArgumentException("image has no layers");
    }
    this.layerVisibility.replace(this.currentLayer, true);
  }


  @Override
  public void loadImage(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("image cannot be null");
    }
    if (this.currentLayer == null) {
      throw new IllegalArgumentException("image has no layers");
    }
    // set the layer dimensions to the first image loaded into the empty canvas
    if (allLayersEmpty()) {
      this.width = image.getWidth();
      this.height = image.getHeight();
    }
    if (image.getWidth() != this.width || image.getHeight() != this.height) {
      throw new IllegalArgumentException("invalid image dimensions");
    }
    this.layerContents.replace(this.currentLayer, image);
  }

  @Override
  public void loadProject(Map<String, IImage> multipleImages) throws IllegalArgumentException {

    if (multipleImages == null) {
      throw new IllegalArgumentException("multipleImages cannot be null");
    }

    // removes everything if there is anything in this.layerContents
    Set<Entry<String, IImage>> set = new HashSet<>();
    for (Map.Entry<String, IImage> i : this.layerContents.entrySet()) {
      set.add(i);
    }

    for (Map.Entry<String, IImage> i : set) {
      this.layerContents.remove(i.getKey());
    }

    // removes everything if there is anything in this.layerVisibility
    Set<Entry<String, Boolean>> visibilitySet = new HashSet<>();

    for (Map.Entry<String, Boolean> i : this.layerVisibility.entrySet()) {
      visibilitySet.add(i);
    }

    for (Map.Entry<String, Boolean> i : visibilitySet) {
      this.layerVisibility.remove(i.getKey());
    }

    // adds the new layers and images
    for (Map.Entry<String, IImage> i : multipleImages.entrySet()) {
      this.layerContents.put(i.getKey(), i.getValue());
      this.currentLayer = i.getKey();
    }

    // sets all the visibilities to true
    for (String s: multipleImages.keySet()) {
      this.layerVisibility.put(s, true);
    }

    this.width = this.layerContents.get(this.currentLayer).getWidth();
    this.height = this.layerContents.get(this.currentLayer).getHeight();

  }

  // determines if each IImage in this layers is empty (null)
  // helper in loadImage to see if dimensions need to be changed or not
  private boolean allLayersEmpty() {
    for (String key : this.layerContents.keySet()) {
      if (this.layerContents.get(key) != null) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void removeImage() throws IllegalArgumentException {
    if (this.currentLayer == null || this.layerContents.size() == 0) {
      throw new IllegalArgumentException("image has no layers.");
    }
    this.layerContents.replace(this.currentLayer, null);
  }

  @Override
  public void applyFilter(IFilter filter) throws IllegalArgumentException {
    if (this.currentLayer == null) {
      throw new IllegalArgumentException("image has no layers");
    }

    this.layerContents.replace(this.currentLayer,
        filter.applyFilter(this.layerContents.get(this.currentLayer)));
  }

  @Override
  public IImage getImage(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("string name cannot be null");
    }
    if (!layerContents.containsKey(name)) {
      throw new IllegalArgumentException(
          "this multilayered image does not contain the given string key!");
    }

    if (this.layerContents.get(name) == null) {
      return null;
    }
    else {
      return this.layerContents.get(name).duplicateImage();
    }
  }

  @Override
  public IImage getVisibleImage() throws IllegalArgumentException {
    List<String> keyList = new ArrayList<>(this.layerVisibility.keySet());

    int index = keyList.size() - 1;
    for (int i = index; i >= 0 ; i--) {
      if (this.layerVisibility.get(keyList.get(i)).equals(true)
          && this.layerContents.get(keyList.get(i)) != null) {
        try {
          return this.layerContents.get(keyList.get(i)).duplicateImage();
        }
        catch (NullPointerException e) {
          throw new IllegalArgumentException("this is null");
        }
      }
    }
    throw new IllegalArgumentException("no visible image");
  }

  @Override
  public Map<String, IImage> getLayerContents() {
    Map<String, IImage> newLayers = new LinkedHashMap<>();
    for (Map.Entry<String, IImage> i : this.layerContents.entrySet()) {
      if (i.getValue() == null) {
        newLayers.put(i.getKey(), null);
      }
      else {
        newLayers.put(i.getKey(), i.getValue().duplicateImage());
      }
    }
    return newLayers;
  }

  @Override
  public String getCurrentLayer() {
    return this.currentLayer;
  }

  /**
   * Returns the Map of the layer visibility for this model.
   * @return A map of the string name to boolean
   */
  public Map<String, Boolean> getLayerVisibility() {
    Map<String, Boolean> newLayersVisibilities = new LinkedHashMap<>();
    for (Map.Entry<String, Boolean> i : this.layerVisibility.entrySet()) {
      if (i.getValue() == null) {
        newLayersVisibilities.put(i.getKey(), null);
      }
      else {
        newLayersVisibilities.put(i.getKey(), i.getValue());
      }
    }
    return newLayersVisibilities;
  }




}
