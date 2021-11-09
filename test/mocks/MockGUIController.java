package mocks;

import controller.IImageController;
import java.io.IOException;
import view.IViewListener;

/**
 * Mock controller that is used to test the controller as an IViewListener.
 * Used in conjunction with MockView to test that handle methods are properly
 *  by the view.
 */
public class MockGUIController implements IImageController, IViewListener {

  private final Appendable log;

  /**
   * Takes in a mock view that simulates action events, and an appendable
   * to log methods calls to.
   * @param a the appendable
   */
  public MockGUIController(Appendable a) {
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
  public void handleCreateLayer(String name) {
    write("handleCreateLayer " + name + "\n");

  }

  @Override
  public void handleCurrent(String name) {
    write("handleCurrent " + name + "\n");
  }

  @Override
  public void handleLoadFile(String path) {
    write("handleLoadFile " + path + "\n");
  }

  @Override
  public void handleBlur() {
    write("handleBlur\n");
  }

  @Override
  public void handleSharpen() {
    write("handleSharpen\n");
  }

  @Override
  public void handleGreyscale() {
    write("handleGreyscale\n");
  }

  @Override
  public void handleSepia() {
    write("handleSepia\n");
  }


  @Override
  public void handleSave(String name, String fileType) {
    write("handleSaveImage " + name + " " + fileType + "\n");
  }

  @Override
  public void handleSaveAll(String folder, String fileType) {
    write("handleSaveLayers " + folder + " " + fileType + "\n");


  }

  @Override
  public void handleInvisible() {
    write("handleInvisible\n");

  }

  @Override
  public void handleVisible() {
    write("handleVisible\n");

  }

  @Override
  public void handleRemoveLayer() {
    write("handleRemoveLayer\n");
  }

  @Override
  public void handleRemoveImage() {
    write("handleRemoveImage\n");
  }


  @Override
  public void handleCopy() {
    write("handleCopy\n");
  }

  @Override
  public void handleLoadCheckerboard(String tileSize, String width, String height) {
    write("handleCreateCheckerboard " + tileSize + " " + width + " " + height + "\n");

  }

  @Override
  public void handleLoadProject(String path) {
    write("handleLoadProject " + path + "\n");

  }

  @Override
  public void handleScript(String path) {
    write("handleScript " + path + "\n");
  }

  // don't need
  @Override
  public void runProgram() {
    return;
  }
}
