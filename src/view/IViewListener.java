package view;

/**
 * Represents operations to handle all possible events the image controller has.
 */
public interface IViewListener {

  /**
   * Handles the event of creating a new layer in the model.
   * @param name the name of the new layer
   */
  void handleCreateLayer(String name);

  /**
   * Handles the event of going to a layer in the model.
   * @param name the name of the layer
   */
  void handleCurrent(String name);

  /**
   * Handles the event of loading an image into  a layer in the model.
   * @param path the path of the image
   */
  void handleLoadFile(String path);

  /**
   * Handles the event of blurring an image in the model.
   */
  void handleBlur();

  /**
   * Handles the event of sharpening an image in the model.
   */
  void handleSharpen();

  /**
   * Handles the event of greyscaling an image in the model.
   */
  void handleGreyscale();

  /**
   * Handles the event of applying speia to an image in the model.
   */
  void handleSepia();

  /**
   * Handles the event of saving the topmost image in the model.
   * @param name the desired name for the file
   * @param fileType the file type for the exported image
   */
  void handleSave(String name, String fileType);

  /**
   * Handles the event of saving all layers of the model to a file.
   * @param folder the name of the folder destination
   * @param fileType the file type for the exported images
   */
  void handleSaveAll(String folder, String fileType);

  /**
   * Handles the event of the setting a layers visibility to invisible in the model.
   */
  void handleInvisible();

  /**
   * Handles the event of the setting of layers visibility to visible in the model.
   */
  void handleVisible();

  /**
   * Handles the event of removing a layer from the model.
   */
  void handleRemoveLayer();

  /**
   * Handles the event of removing an image from it's layer.
   */
  void handleRemoveImage();

  /**
   * Handles the event of copying a layer in the model.
   */
  void handleCopy();

  /**
   * Handles the event of loading a checkerboard image into a layer.
   * @param tileSize the tileSize for the checkerboard.
   * @param width width in tiles
   * @param height height in tiles
   * @throws IllegalArgumentException if given string is not a number
   */
  void handleLoadCheckerboard(String tileSize, String width, String height) throws
      IllegalArgumentException;

  /**
   * Handles the event of loading in a project in the model.
   * @param path the path of the project that will be loaded
   */
  void handleLoadProject(String path);

  /**
   * Runs and loads the given script in the model.
   * @param path the string path of the script
   */
  void handleScript(String path);


}