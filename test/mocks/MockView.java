package mocks;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import view.IInteractiveView;
import view.IViewListener;

/**
 * A mock GUI view used for testing the the view is properly communicating with
 * the controller.
 * Used in conjunction with the MockGUIController to log communication between the two.
 */
public class MockView implements IInteractiveView {

  private IViewListener l;
  private final Appendable log;

  public MockView(Appendable a) {
    this.log = a;
  }

  private void write(String s) {
    try {
      this.log.append(s);
    }
    catch (IOException e) {
      throw new IllegalStateException("appendable failed");
    }
  }

  @Override
  public void addLayer(String name) {
    write("addLayer: " + name + "\n");
  }

  @Override
  public void removeLayer(String name) {
    write("removeLayer: " + name + "\n");
  }

  @Override
  public void setDisplayImage(BufferedImage image) {
    write("setDisplayImage");
  }
  
  @Override
  public void addListener(IViewListener l) {
    this.l = Objects.requireNonNull(l);
  }

  @Override
  public void displayMessage(String message) {
    write("displayMessage: " + message + "\n");
  }

  @Override
  public void setCurrentLayerName(String s) {
    write("setCurrentLayerName: " + s + "\n");
  }

  public void fireVisibleEvent() {
    l.handleVisible();
  }

  public void fireInVisibleEvent() {
    l.handleInvisible();
  }

  public void fireRemoveLayerEvent() {
    l.handleRemoveLayer();
  }

  public void fireDuplicateLayerEvent() {
    l.handleCopy();
  }

  public void fireRemoveImageEvent() {
    l.handleRemoveImage();
  }

  public void fireCreateLayerEvent(String s) {
    l.handleCreateLayer(s);
  }

  public void fireCreateCheckerboardEvent(String t, String w, String h) {
    l.handleLoadCheckerboard(t, w, h);
  }

  public void fireOpenFileEvent(String path) {
    l.handleLoadFile(path);
  }

  public void fireOpenProjectEvent(String path) {
    l.handleLoadProject(path);
  }

  public void fireSaveFileEvent(String n, String type) {
    l.handleSave(n, type);
  }

  public void fireSaveLayersEvent(String f, String type) {
    l.handleSaveAll(f, type);
  }

  public void fireCurrentEvent(String s) {
    l.handleCurrent(s);
  }

  public void fireBlurEvent() {
    l.handleBlur();
  }

  public void fireSharpenEvent() {
    l.handleSharpen();
  }

  public void fireSepiaEvent() {
    l.handleSepia();
  }

  public void fireGreyscaleEvent() {
    l.handleGreyscale();
  }

  public void fireLoadScriptEvent(String path) {
    l.handleScript(path);
  }

}
