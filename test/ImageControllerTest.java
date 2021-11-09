import controller.IImageController;
import controller.ImageGUIController;
import controller.ImageScriptController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import mocks.ConfirmInputsLayeredModel;
import mocks.MockView;
import model.CheckerboardImage;
import model.IImage;
import model.LayeredImageModel;
import model.ILayeredImageModel;
import org.junit.Before;
import org.junit.Test;
import view.IInteractiveView;
import view.InteractiveView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


/**
 * Tests for the ImageController class.
 */
public class ImageControllerTest {

  private ILayeredImageModel layeredImageModel;

  private StringReader stringReader1;

  private StringBuilder stringBuilder1;
  private StringBuilder stringBuilderForMock;

  // script controllers
  private IImageController imageScriptController2;
  private IImageController imageScriptController3;
  private IImageController imageScriptControllerConfirmModelInputs;
  private IImageController imageScriptControllerReadScript;
  private IImageController imageScriptControllerReadScriptInvalidCommands;

  // GUI controllers
  private IInteractiveView gui1;
  private ImageGUIController imageGUIController1;
  StringBuilder s;
  MockView mockView;
  private ImageGUIController imageGUIControllerMockView; // test communication to the view

  @Before
  public void initImageScriptControllers() {
    this.stringBuilder1 = new StringBuilder();
    this.stringBuilderForMock = new StringBuilder();

    this.layeredImageModel = new LayeredImageModel();
    ConfirmInputsLayeredModel confirmInputsModel = new
        ConfirmInputsLayeredModel(this.stringBuilderForMock);

    // every command for the model
    this.stringReader1 = new StringReader("create-layer one current one copy "
        + "blur sharpen greyscale sepia save saveAll invisible visible "
        + "remove-image remove-layer");
    StringReader stringReader2 = new StringReader("hows it going bro");
    StringReader stringReader3 = new StringReader("q");

    IImageController imageScriptController1 = new ImageScriptController(this.layeredImageModel,
        this.stringReader1, this.stringBuilder1);
    this.imageScriptControllerConfirmModelInputs = new ImageScriptController(confirmInputsModel,
        this.stringReader1, this.stringBuilderForMock);
    this.imageScriptController2 = new ImageScriptController(this.layeredImageModel, stringReader2,
        this.stringBuilder1);
    this.imageScriptController3 = new ImageScriptController(this.layeredImageModel, stringReader3,
        this.stringBuilder1);

    try {
      this.imageScriptControllerReadScript = new ImageScriptController(this.layeredImageModel,
          new FileReader("test/scripts/testScript1.txt"), this.stringBuilder1);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("something happened");
    }

    try {
      this.imageScriptControllerReadScriptInvalidCommands = new ImageScriptController(
          this.layeredImageModel, new FileReader("test/scripts/testScriptInvalidCommands.txt"),
          this.stringBuilder1);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("something happened");
    }

  }

  @Before
  public void initGUIControllers() {
    this.gui1 = new InteractiveView();

    this.imageGUIController1 = new ImageGUIController(this.layeredImageModel, this.gui1);

    s = new StringBuilder();
    mockView = new MockView(s);
    this.imageGUIControllerMockView = new ImageGUIController(this.layeredImageModel, this.mockView);
  }


  // ImageScriptController tests ---------------------------------------------------------------

  @Test (expected = IllegalArgumentException.class)
  public void testNullModel() {
    new ImageScriptController(null, this.stringReader1, this.stringBuilder1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadable() {
    new ImageScriptController(this.layeredImageModel, null, this.stringBuilder1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new ImageScriptController(this.layeredImageModel, this.stringReader1, null);
  }

  @Test
  public void testEveryModelCommand() {
    StringBuilder expected = new StringBuilder();

    expected.append("you may start typing commands\n")
        .append("created layer: one\ncommand: create-layer executed.\ngo to layer: one\n")
        .append("command: current executed.\ncreated layer copy\ncommand: copy executed.\n")
        .append("applied filter\ncommand: blur executed.\n")
        .append("applied filter\ncommand: sharpen executed.\n")
        .append("applied filter\ncommand: greyscale executed.\n")
        .append("applied filter\ncommand: sepia executed.\n")
        .append("command failed. reason: not a valid string for ExportType\nmade layer\n")
        .append("command: visible executed.\nremoved image\ncommand: remove-image executed.\n")
        .append("removed layer\ncommand: remove-layer executed.\n");

    this.imageScriptControllerConfirmModelInputs.runProgram();
    assertEquals(expected.toString(), this.stringBuilderForMock.toString());
  }

  @Test
  public void testScript() {
    StringBuilder expected = new StringBuilder();
    expected.append("you may start typing commands\n")
        .append("command: create-layer executed.\ncommand: load-checkerboard executed.\n")
        .append("command: create-layer executed.\ninvalid command\n")
        .append("invalid command\ncommand: current executed.\ncommand: copy executed.\n")
        .append("command: sepia executed.\ncommand: blur executed.\ncommand: sharpen executed.\n")
        .append("command: greyscale executed.\ncommand: invisible executed.\n")
        .append("command: visible executed.\ncommand: remove-layer executed.\n");

    this.imageScriptControllerReadScript.runProgram();

    assertEquals(expected.toString(), this.stringBuilder1.toString());
  }

  @Test
  public void testScriptAllCommandsFail() {
    StringBuilder expected = new StringBuilder();

    expected
        .append("you may start typing commands\n")
        .append("command: create-layer executed.\ncommand failed. reason: layer with that name ")
        .append("already exists\ncommand failed. reason: key does not exist\n")
        .append("command failed. reason: given filename is not a valid fileType.\n")
        .append("command failed. reason: image cannot be null!\ncommand failed. reason: image ")
        .append("cannot be null!\ncommand failed. reason: image cannot be null!\n")
        .append("command failed. reason: image cannot be null!\ncommand failed. reason: not a ")
        .append("valid string for ExportType\ncommand failed. reason: not a valid string for ")
        .append("ExportType\ncommand: remove-image executed.\ncommand: remove-layer executed.\n")
        .append("command failed. reason: there are no layers\ncommand failed. reason: image has ")
        .append("no layers\ncommand failed. reason: image has no layers\n")
        .append("command failed. reason: there are no layers\ninvalid command\ninvalid command\n")
        .append("invalid command\n");

    this.imageScriptControllerReadScriptInvalidCommands.runProgram();

    assertEquals(expected.toString(), this.stringBuilder1.toString());
  }

  @Test
  public void testOnlyInvalidCommands() {
    StringBuilder expected = new StringBuilder("you may start typing commands\n");

    expected.append("invalid command\ninvalid command\ninvalid command\ninvalid command\n");

    this.imageScriptController2.runProgram();

    assertEquals(expected.toString(), this.stringBuilder1.toString());
  }

  @Test
  public void testOnlyQuit() {
    StringBuilder expected = new StringBuilder("you may start typing commands\n");

    this.imageScriptController3.runProgram();

    assertEquals(expected.toString(), this.stringBuilder1.toString());
  }


  // ImageGUIController tests ---------------------------------------------------------------

  @Test (expected = IllegalArgumentException.class)
  public void testNullModelForGUI() {
    new ImageGUIController(null, this.gui1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullViewForGUI() {
    new ImageGUIController(this.layeredImageModel, null);
  }


  @Test
  public void testHandleCreateLayer() {

    this.imageGUIController1.handleCreateLayer("hello");
    assertEquals(null, this.layeredImageModel.getImage("hello"));

    this.imageGUIControllerMockView.handleCreateLayer("hello!");
    assertEquals("addLayer: hello!\nsetCurrentLayerName: hello!\n", s.toString());

  }

  @Test
  public void testHandleCurrent() {
    this.imageGUIController1.handleCreateLayer("cat");
    this.imageGUIController1.handleCreateLayer("dog");
    this.imageGUIController1.handleCurrent("cat");
    assertEquals("cat", this.layeredImageModel.getCurrentLayer());

    this.imageGUIControllerMockView.handleCurrent("dog");
    assertEquals("setDisplayImagesetCurrentLayerName: dog\n", s.toString());

  }

  @Test
  public void testHandleLoadFile() {

    this.imageGUIController1.handleCreateLayer("hello");
    assertEquals(null, this.layeredImageModel.getImage("hello"));
    this.imageGUIController1.handleLoadFile("res/Dog.ppm");
    assertNotEquals(null, this.layeredImageModel.getImage("hello"));

    this.imageGUIControllerMockView.handleLoadFile("res/Dog.ppm");
    assertEquals("setDisplayImage", s.toString());

  }


  @Test
  public void testHandleBlur() {
    // check controller is correctly calling on view to change
    this.imageGUIControllerMockView.handleCreateLayer("1");
    this.imageGUIControllerMockView.handleBlur();
    assertEquals("addLayer: 1\nsetCurrentLayerName: 1\n"
        + "displayMessage: blur failed: image cannot be null!\n", s.toString());
    this.imageGUIControllerMockView.handleLoadCheckerboard("1", "1", "1");
    this.imageGUIControllerMockView.handleBlur();
    assertEquals("addLayer: 1\nsetCurrentLayerName: 1\ndisplayMessage: blur failed: "
        + "image cannot be null!\nsetDisplayImagesetDisplayImage", s.toString());
  }

  @Test
  public void testHandleSharpen() {
    // check controller is correctly calling on view to change
    this.imageGUIControllerMockView.handleCreateLayer("1");
    this.imageGUIControllerMockView.handleSharpen();
    assertEquals("addLayer: 1\nsetCurrentLayerName: 1\n"
        + "displayMessage: sharpen failed: image cannot be null!\n", s.toString());
    this.imageGUIControllerMockView.handleLoadCheckerboard("1", "1", "1");
    this.imageGUIControllerMockView.handleSharpen();
    assertEquals("addLayer: 1\nsetCurrentLayerName: 1\ndisplayMessage: sharpen failed: "
        + "image cannot be null!\nsetDisplayImagesetDisplayImage", s.toString());
  }

  @Test
  public void testHandleGreyscale() {
    // check controller is correctly calling on view to change
    this.imageGUIControllerMockView.handleCreateLayer("1");
    this.imageGUIControllerMockView.handleGreyscale();
    assertEquals("addLayer: 1\nsetCurrentLayerName: 1\n"
        + "displayMessage: greyscale failed: image cannot be null!\n", s.toString());
    this.imageGUIControllerMockView.handleLoadCheckerboard("1", "1", "1");
    this.imageGUIControllerMockView.handleGreyscale();
    assertEquals("addLayer: 1\nsetCurrentLayerName: 1\ndisplayMessage: greyscale failed: "
        + "image cannot be null!\nsetDisplayImagesetDisplayImage", s.toString());
  }

  @Test
  public void testHandleSepia() {
    // check controller is correctly calling on view to change
    this.imageGUIControllerMockView.handleCreateLayer("1");
    this.imageGUIControllerMockView.handleSepia();
    assertEquals("addLayer: 1\nsetCurrentLayerName: 1\n"
        + "displayMessage: sepia failed: image cannot be null!\n", s.toString());
    this.imageGUIControllerMockView.handleLoadCheckerboard("1", "1", "1");
    this.imageGUIControllerMockView.handleSepia();
    assertEquals("addLayer: 1\nsetCurrentLayerName: 1\ndisplayMessage: sepia failed: "
        + "image cannot be null!\nsetDisplayImagesetDisplayImage", s.toString());
  }

  @Test
  public void testHandleSave() {
    this.imageGUIController1.handleCreateLayer("hello");
    this.imageGUIController1.handleLoadCheckerboard("1", "1", "1");

    // make sure new file doesn't already exist
    File testFile = new File("coolPicture.png");
    testFile.delete();

    this.imageGUIController1.handleSave("coolPicture", "png");

    Path path = null;
    try {
      path = Files.createTempFile("coolPicture", ".png");
    } catch (IOException e) {
      //
    }
    assertTrue(Files.exists(path));

    // delete new file from test
    testFile.delete();

    // bad save displayed on view
    this.imageGUIControllerMockView.handleSave("hello", "jng");
    assertEquals("displayMessage: save failed: not a valid string for ExportType\n",
        s.toString());

  }

  @Test
  public void testHandleSaveAll() {

    this.imageGUIController1.handleCreateLayer("hello");
    this.imageGUIController1.handleLoadCheckerboard("4", "1", "1");
    this.imageGUIController1.handleCreateLayer("hello2");
    this.imageGUIController1.handleLoadCheckerboard("1", "4", "4");

    // make sure new file doesn't already exist
    File testFile = new File("aNewFolder/hello.jpg");
    testFile.delete();
    File testFile1 = new File("aNewFolder/hello2.jpg");
    testFile1.delete();
    File testFile3 = new File("aNewFolder/aNewFolder.txt");
    testFile3.delete();
    File testFile2 = new File("aNewFolder");
    testFile2.delete();

    this.imageGUIController1.handleSaveAll("aNewFolder", "jpg");

    Path path = null;
    Path path1 = null;
    Path path2 = null;
    try {
      path = Files.createTempDirectory("aNewFolder");
      path1 = Files.createTempFile(path, "hello", ".jpg");
      path2 = Files.createTempFile(path, "hello2", ".jpg");
    } catch (IOException e) {
      //
    }
    assertTrue(Files.exists(path));
    assertTrue(Files.exists(path1));
    assertTrue(Files.exists(path2));

    // delete new file from test
    testFile.delete();
    testFile1.delete();
    testFile3.delete();
    testFile2.delete();

    // bad save displayed on the view
    this.imageGUIControllerMockView.handleSaveAll("duck", "jng");
    assertEquals("displayMessage: project save failed: not a valid string for ExportType\n",
        s.toString());
  }

  @Test
  public void testHandleVisible() {

    this.imageGUIController1.handleCreateLayer("hello");
    this.imageGUIController1.handleLoadCheckerboard("1", "1", "1");
    this.imageGUIController1.handleVisible();
    assertTrue(this.layeredImageModel.getLayerVisibility().get("hello"));

    this.imageGUIControllerMockView.handleVisible();
    assertEquals("setDisplayImage", s.toString());

  }

  @Test
  public void testHandleInvisible() {

    this.imageGUIController1.handleCreateLayer("hello");
    this.imageGUIController1.handleLoadCheckerboard("1", "1", "1");
    this.imageGUIController1.handleInvisible();
    assertFalse(this.layeredImageModel.getLayerVisibility().get("hello"));

    this.imageGUIControllerMockView.handleInvisible();
    assertEquals("setDisplayImage", s.toString());
  }

  @Test
  public void testHandleRemoveLayer() {
    this.imageGUIController1.handleCreateLayer("hello");
    this.imageGUIController1.handleCreateLayer("hello2");
    assertEquals(2, this.layeredImageModel.getLayerContents().size());
    this.imageGUIController1.handleRemoveLayer();
    assertEquals(1, this.layeredImageModel.getLayerContents().size());

    this.imageGUIControllerMockView.handleRemoveLayer();
    assertEquals("removeLayer: null\nsetDisplayImagesetCurrentLayerName: hello\n",
        s.toString());
  }


  @Test
  public void testHandleRemoveImage() {
    IImage checkerboard = new CheckerboardImage(1, 1, 1).createCheckerboard();

    this.imageGUIControllerMockView.handleCreateLayer("hello");
    this.imageGUIControllerMockView.handleLoadCheckerboard("1", "1", "1");
    assertEquals(checkerboard, this.layeredImageModel.getImage("hello"));


    this.imageGUIControllerMockView.handleRemoveImage();
    assertEquals("addLayer: hello\nsetCurrentLayerName: hello\nsetDisplayImagesetDisplayImage",
        s.toString());

  }

  @Test
  public void testHandleCopyWithImage() {
    this.imageGUIController1.handleCreateLayer("hello");
    assertEquals(null, this.layeredImageModel.getImage("hello"));

    this.imageGUIController1.handleLoadCheckerboard("1", "1", "1");
    assertNotEquals(null, this.layeredImageModel.getImage("hello"));
    assertEquals(1, this.layeredImageModel.getLayerContents().size());
    this.imageGUIController1.handleCopy();
    assertNotEquals(null, this.layeredImageModel.getImage("hello copy"));
    assertEquals(2, this.layeredImageModel.getLayerContents().size());

    this.imageGUIControllerMockView.handleCopy();
    assertEquals("addLayer: hello copy copy\nsetDisplayImagesetCurrentLayerName: "
            + "hello copy copy\n",
        s.toString());
  }


  @Test
  public void testHandleCopyWithoutImage() {

    this.imageGUIController1.handleCreateLayer("hello");
    assertEquals(null, this.layeredImageModel.getImage("hello"));
    assertEquals(1, this.layeredImageModel.getLayerContents().size());
    this.imageGUIController1.handleCopy();
    assertEquals(null, this.layeredImageModel.getImage("hello copy"));
    assertEquals(2, this.layeredImageModel.getLayerContents().size());
    this.imageGUIControllerMockView.handleCopy();
    assertEquals("addLayer: hello copy copy\nsetDisplayImagesetCurrentLayerName: "
            + "hello copy copy\n",
        s.toString());
  }

  @Test
  public void testHandleLoadCheckerboard() {
    IImage checkerboard = new CheckerboardImage(1, 1, 1).createCheckerboard();

    this.imageGUIController1.handleCreateLayer("hello");
    this.imageGUIController1.handleLoadCheckerboard("1", "1", "1");
    assertEquals(checkerboard, this.layeredImageModel.getImage("hello"));
    this.imageGUIControllerMockView.handleLoadCheckerboard("1", "1", "2");
    assertEquals("displayMessage: load checkerboard failed: invalid image dimensions\n",
        s.toString());
  }

  @Test
  public void testHandleLoadProject() {

    assertEquals(0, this.layeredImageModel.getLayerContents().size());
    this.imageGUIController1.handleLoadProject("res/dogFolder");
    assertEquals(3, this.layeredImageModel.getLayerContents().size());
    this.imageGUIControllerMockView.handleLoadProject("res/dogFolder");
    assertEquals("addLayer: cat\naddLayer: fish\naddLayer: dog\n"
        + "setDisplayImagesetCurrentLayerName: dog\n", s.toString());
  }

  @Test
  public void testHandleScript() {

    assertEquals(0, this.layeredImageModel.getLayerContents().size());
    this.imageGUIController1.handleScript("test/scripts/scriptForTesting.txt");
    assertEquals(2, this.layeredImageModel.getLayerContents().size());
    this.imageGUIControllerMockView.handleScript("test/scripts/scriptForTesting.txt");
    assertEquals("addLayer: one\naddLayer: two\nsetCurrentLayerName: two\nsetDisplayImage",
        s.toString());

  }






}
