import controller.IImageController;
import mocks.MockView;
import org.junit.Before;
import org.junit.Test;
import view.IInteractiveView;
import view.IViewListener;
import view.InteractiveView;
import mocks.MockGUIController;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the InteractiveView. Focuses on testing communication to the controller
 * and internal state as we cannot test the GUI window itself.
 */
public class InteractiveViewTest {

  private MockView mockGUI1;

  private StringBuilder stringBuilder2;

  @Before
  public void init() {
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder2 = new StringBuilder();
    this.mockGUI1 = new MockView(stringBuilder1);
    IInteractiveView gui1 = new InteractiveView();
    IImageController controller1 = new MockGUIController(stringBuilder2);
    this.mockGUI1.addListener((IViewListener) controller1);
    IImageController controller2 = new MockGUIController(stringBuilder2);
    gui1.addListener((IViewListener) controller2);

  }

  // wiring tests to check proper communication for methods calls to the controller after an event

  @Test
  public void testWiringToVisibleEvent() {
    this.mockGUI1.fireVisibleEvent();
    assertEquals("handleVisible\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToCreateLayerEvent() {
    this.mockGUI1.fireCreateLayerEvent("hello!");
    assertEquals("handleCreateLayer hello!\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToCurrentEvent() {
    this.mockGUI1.fireCurrentEvent("Hello!");
    assertEquals("handleCurrent Hello!\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToLoadFileEvent() {
    this.mockGUI1.fireOpenFileEvent("dog.jpg");
    assertEquals("handleLoadFile dog.jpg\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToBlurEvent() {
    this.mockGUI1.fireBlurEvent();
    assertEquals("handleBlur\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToSharpenEvent() {
    this.mockGUI1.fireSharpenEvent();
    assertEquals("handleSharpen\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToGreyscaleEvent() {
    this.mockGUI1.fireGreyscaleEvent();
    assertEquals("handleGreyscale\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToSepiaEvent() {
    this.mockGUI1.fireSepiaEvent();
    assertEquals("handleSepia\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToSaveEvent() {
    this.mockGUI1.fireSaveFileEvent("dog", "png");
    assertEquals("handleSaveImage dog png\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToSaveLayersEvent() {
    this.mockGUI1.fireSaveLayersEvent("dogs", "ppm");
    assertEquals("handleSaveLayers dogs ppm\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToInvisibleEvent() {
    this.mockGUI1.fireInVisibleEvent();
    assertEquals("handleInvisible\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToRemoveLayerEvent() {
    this.mockGUI1.fireRemoveLayerEvent();
    assertEquals("handleRemoveLayer\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToRemoveImageEvent() {
    this.mockGUI1.fireRemoveImageEvent();
    assertEquals("handleRemoveImage\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToCopyEvent() {
    this.mockGUI1.fireDuplicateLayerEvent();
    assertEquals("handleCopy\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToLoadCheckerboardEvent() {
    this.mockGUI1.fireCreateCheckerboardEvent("1", "2", "3");
    assertEquals("handleCreateCheckerboard 1 2 3\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToLoadProjectEvent() {
    this.mockGUI1.fireOpenProjectEvent("dogs");
    assertEquals("handleLoadProject dogs\n", this.stringBuilder2.toString());
  }

  @Test
  public void testWiringToLoadScriptEvent() {
    this.mockGUI1.fireLoadScriptEvent("fun.txt");
    assertEquals("handleScript fun.txt\n", this.stringBuilder2.toString());
  }

  // tests for public methods



}
