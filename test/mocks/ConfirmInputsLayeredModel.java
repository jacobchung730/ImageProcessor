package mocks;

import java.util.Map;
import model.IImage;
import model.ILayeredImageModel;
import model.filter.IFilter;

/**
 * Mock model for the ILayeredImageModel. This is used to show that the model's inputs are
 * correctly outputted by the controller. This class is used in testing.
 */
public class ConfirmInputsLayeredModel implements ILayeredImageModel {

  StringBuilder log;

  public ConfirmInputsLayeredModel(StringBuilder s) {
    this.log = s;
  }

  @Override
  public void createLayer(String name) throws IllegalArgumentException {
    this.log.append("created layer: " + name + "\n");
  }

  @Override
  public void createLayerCopy() {
    this.log.append("created layer copy\n");
  }

  @Override
  public void removeLayer() throws IllegalArgumentException {
    this.log.append("removed layer\n");
  }

  @Override
  public void goToLayer(String name) throws IllegalArgumentException {
    this.log.append("go to layer: " + name + "\n");
  }

  @Override
  public void makeLayerInvisible() throws IllegalArgumentException {
    this.log.append("made layer invisible\n");
  }

  @Override
  public void makeLayerVisible() throws IllegalArgumentException {
    this.log.append("made layer\n");
  }


  @Override
  public void loadImage(IImage image) throws IllegalArgumentException {
    this.log.append("loaded image\n");
  }

  @Override
  public void loadProject(Map<String, IImage> multipleImages) throws IllegalArgumentException {
    this.log.append("loaded project\n");
  }


  @Override
  public void removeImage() throws IllegalArgumentException {
    this.log.append("removed image\n");
  }

  @Override
  public void applyFilter(IFilter filter) throws IllegalArgumentException {
    this.log.append("applied filter\n");
  }

  @Override
  public IImage getImage(String name) throws IllegalArgumentException {
    this.log.append("current image gotten\n");
    return null;
  }

  @Override
  public IImage getVisibleImage() throws IllegalArgumentException {
    this.log.append("topmost image gotten\n");
    return null;
  }

  @Override
  public Map<String, IImage> getLayerContents() {
    this.log.append("all layers gotten\n");
    return null;
  }

  @Override
  public String getCurrentLayer() {
    return null;
  }

  @Override
  public Map<String, Boolean> getLayerVisibility() {
    return null;
  }

}
