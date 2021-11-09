package controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.CheckerboardImage;
import model.ExportType;
import model.IImage;
import model.ILayeredImageModel;
import model.Image;
import model.filter.Blur;
import model.filter.Greyscale;
import model.filter.Sepia;
import model.filter.Sharpen;
import view.IInteractiveView;
import view.IViewListener;

/**
 * Represents the controller for the GUI view. This controller takes in a ILayeredImageModel
 * and an IInteractiveView. This class has methods from the IViewListener interface which is used
 * when having the controller tell the model what command should be called.
 */
public class ImageGUIController implements IImageController, IViewListener {

  private final ILayeredImageModel model;
  private final IInteractiveView view;

  /**
   * Constructs a new GUI view controller.
   * @param model the model for this controller
   * @param view the view for this controller (GUI)
   * @throws IllegalArgumentException if the model or view are null
   */
  public ImageGUIController(ILayeredImageModel model, IInteractiveView view) throws
      IllegalArgumentException {

    if (model == null || view == null) {
      throw new IllegalArgumentException("model or view cannot be null");
    }

    this.model = model;
    this.view = view;
    this.view.addListener(this);

  }

  @Override
  public void runProgram() throws IllegalStateException {
    // this method is empty in the GUI controller because the GUI is
    // able to be called when instantiating it by calling frame.setVisible(true)
  }

  @Override
  public void handleCreateLayer(String name) {
    try {
      this.model.createLayer(name);
      this.view.addLayer(name);
      this.view.setCurrentLayerName(name);
    }
    catch (IllegalArgumentException e) {
      this.view.displayMessage("layer creation failed: " + e.getMessage());
    }

  }

  @Override
  public void handleCurrent(String name) {
    try {
      this.model.goToLayer(name);
      getTopMostVisibleImage();
      this.view.setCurrentLayerName(name);
    } catch (IllegalArgumentException e) {
      this.view.displayMessage("current failed:" + e.getMessage());
    }
  }

  @Override
  public void handleLoadFile(String path) {

    try {
      this.model.loadImage(new Image(ImageUtil.readFile(path)));
      this.view.setDisplayImage(ImageUtil.readFileToBufferedImage(path));
    } catch (IllegalArgumentException e) {
      this.view.displayMessage("loadFile failed:" + e.getMessage());
    }
  }

  @Override
  public void handleBlur() {
    try {
      this.model.applyFilter(new Blur());
      getTopMostVisibleImage();
    } catch (IllegalArgumentException e) {
      this.view.displayMessage("blur failed: " + e.getMessage());
    }
  }

  @Override
  public void handleSharpen() {

    try {
      this.model.applyFilter(new Sharpen());
      getTopMostVisibleImage();
    } catch (IllegalArgumentException e) {
      this.view.displayMessage("sharpen failed: " + e.getMessage());
    }
  }

  @Override
  public void handleGreyscale() {
    try {
      this.model.applyFilter(new Greyscale());
      getTopMostVisibleImage();
    } catch (IllegalArgumentException e) {
      this.view.displayMessage("greyscale failed: " + e.getMessage());
    }
  }

  @Override
  public void handleSepia() {
    try {
      this.model.applyFilter(new Sepia());

      getTopMostVisibleImage();
    } catch (IllegalArgumentException e) {
      this.view.displayMessage("sepia failed: " + e.getMessage());
    }
  }


  @Override
  public void handleSave(String name, String fileType) {
    try {
      ImageUtil.exportImage(name, ExportType.getEnum(fileType), this.model.getVisibleImage());
    }
    catch (IllegalArgumentException e) {
      this.view.displayMessage("save failed: " + e.getMessage());
    }

  }

  @Override
  public void handleSaveAll(String folder, String fileType) {
    try {
      ImageUtil.exportLayeredImage(this.model, folder, ExportType.getEnum(fileType));
    }
    catch (IllegalArgumentException e) {
      this.view.displayMessage("project save failed: " + e.getMessage());
    }

  }

  @Override
  public void handleInvisible() {

    try {
      this.model.makeLayerInvisible();

      getTopMostVisibleImage();
    } catch (IllegalArgumentException e) {
      this.view.displayMessage("invisible failed: " + e.getMessage());
    }
  }

  @Override
  public void handleVisible() {
    try {
      this.model.makeLayerVisible();

      getTopMostVisibleImage();

    } catch (IllegalArgumentException e) {
      this.view.displayMessage("visible save failed: " + e.getMessage());
    }
  }

  @Override
  public void handleRemoveLayer() {
    try {
      String currentLayer = this.model.getCurrentLayer();
      this.model.removeLayer();

      this.view.removeLayer(currentLayer);

      if (this.model.getCurrentLayer() == null) {
        this.view.setDisplayImage(null);
        this.view.setCurrentLayerName("");
      } else {
        getTopMostVisibleImage();
        this.view.setCurrentLayerName(this.model.getCurrentLayer());
      }
    }
    catch (IllegalArgumentException e) {
      this.view.displayMessage("remove layer failed: " + e.getMessage());
    }
  }

  @Override
  public void handleRemoveImage() {

    try {
      this.model.removeImage();
      getTopMostVisibleImage();
    } catch (IllegalArgumentException e) {
      this.view.displayMessage("remove image failed: " + e.getMessage());
    }
  }


  @Override
  public void handleCopy() {
    try {
      this.model.createLayerCopy();

      this.view.addLayer(this.model.getCurrentLayer());
      getTopMostVisibleImage();
      this.view.setCurrentLayerName(this.model.getCurrentLayer());
    }
    catch (IllegalArgumentException e) {
      this.view.displayMessage("copy failed: " + e.getMessage());
    }
  }

  @Override
  public void handleLoadCheckerboard(String tileSize, String width, String height) {

    try {
      int tileSizeInt;
      int widthInt;
      int heightInt;

      try {
        tileSizeInt = Integer.parseInt(tileSize);
        widthInt = Integer.parseInt(width);
        heightInt = Integer.parseInt(height);

      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("invalid number");
      }

      this.model
          .loadImage(new CheckerboardImage(tileSizeInt, widthInt, heightInt).createCheckerboard());

      getTopMostVisibleImage();
    } catch (IllegalArgumentException e) {
      this.view.displayMessage("load checkerboard failed: " + e.getMessage());
    }
  }

  @Override
  public void handleLoadProject(String path) {

    try {

      // loads the project (in the model)
      this.model.loadProject(ImageUtil.readLayeredImage(path));

      for (Map.Entry<String, IImage> e : this.model.getLayerContents().entrySet()) {
        this.view.addLayer(e.getKey());
      }

      getTopMostVisibleImage();

      this.view.setCurrentLayerName(this.model.getCurrentLayer());

    } catch (IllegalArgumentException e) {
      this.view.displayMessage("load project failed: " + e.getMessage());
    }

  }

  @Override
  public void handleScript(String path) {
    File scriptPath = new File(path);
    IImageController scriptController;
    try {
      scriptController = new ImageScriptController(this.model,
          new FileReader(scriptPath), new StringBuilder());
      scriptController.runProgram();

      // update layers and display image
      for (String s : this.model.getLayerContents().keySet()) {
        this.view.addLayer(s);
      }

      this.view.setCurrentLayerName(this.model.getCurrentLayer());

      //setNewImage();
      getTopMostVisibleImage();

    } catch (FileNotFoundException e) {
      this.view.displayMessage("load script failed: " + e.getMessage());
    }
  }

  // set the new Image to show on the layer
  private void setNewImage() {
    try {
      IImage newImage = this.model.getImage(this.model.getCurrentLayer());
      this.view.setDisplayImage(ImageUtil.getIImageToBufferedImage(newImage));
    } catch (IllegalArgumentException e) {
      this.view.setDisplayImage(null);
    }
  }


  // displays the top most visible image (can either be blank or an image)
  private void getTopMostVisibleImage() {
    boolean imageFound = false;
    List<String> layers = new ArrayList<>(this.model.getLayerContents().keySet());
    for (int i = layers.size() - 1; i >= 0; i--) {
      String key = layers.get(i);
      if (this.model.getImage(key) != null && this.model.getLayerVisibility().get(key)) {
        try {
          IImage newImage = this.model.getImage(key);
          this.view.setDisplayImage(ImageUtil.getIImageToBufferedImage(newImage));
          imageFound = true;
        } catch (IllegalArgumentException e) {
          // do nothing, an empty layer is selected
        }
        break;
      }
    }

    // no visible images
    if (!imageFound) {
      this.view.setDisplayImage(null);
    }

  }

}
