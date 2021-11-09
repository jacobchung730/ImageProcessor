import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import controller.commands.BlurCommand;
import controller.commands.Copy;
import controller.commands.CreateLayer;
import controller.commands.Current;
import controller.commands.GreyscaleCommand;
import controller.commands.Invisible;
import controller.commands.LoadCheckerboard;
import controller.commands.LoadFile;
import controller.commands.LoadProject;
import controller.commands.RemoveImage;
import controller.commands.RemoveLayer;
import controller.commands.SaveAll;
import controller.commands.SepiaCommand;
import controller.commands.SharpenCommand;
import controller.commands.Visible;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import model.CheckerboardImage;
import model.IImage;
import model.ILayeredImageModel;
import model.LayeredImageModel;
import model.filter.Blur;
import model.filter.Greyscale;
import model.filter.Sepia;
import model.filter.Sharpen;
import org.junit.Test;

/**
 * Tests for all the command classes in from the script controller.
 */
public class CommandTest {

  private ILayeredImageModel model = new LayeredImageModel();
  private ILayeredImageModel modelCopy = new LayeredImageModel();
  private IImage image1 = new CheckerboardImage(4, 1, 1).createCheckerboard();
  private IImage image1Copy = new CheckerboardImage(4, 1, 1).createCheckerboard();
  private IImage image2 = new CheckerboardImage(2, 2, 2).createCheckerboard();
  private IImage image3 = new CheckerboardImage(1, 4, 4).createCheckerboard();
  private IImage image4 = new CheckerboardImage(1, 1, 1).createCheckerboard();
  private IImage wrongDimensionsImage =
      new CheckerboardImage(10, 10, 10).createCheckerboard();
  private Map<String, IImage> listOfImages = new LinkedHashMap<>();

  @Test
  public void testBlurCommand() {
    model.createLayer("dog");
    model.loadImage(image1);


    modelCopy.createLayer("dog");
    modelCopy.loadImage(image1Copy);

    assertEquals(model.getImage("dog"), modelCopy.getImage("dog"));

    // blur one of the images and check that they are different now
    new BlurCommand().applyCommand(model);
    assertNotEquals(model.getImage("dog"), modelCopy.getImage("dog"));

    // check that image actually got blurred
    assertEquals(new Blur().applyFilter(image1Copy), model.getImage("dog"));

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullBlurModel() {
    new BlurCommand().applyCommand(null);
  }

  @Test
  public void testCopyCommand() {

    assertEquals(0, model.getLayerContents().size());
    model.createLayer("dog");
    model.loadImage(image1);
    assertEquals(1, model.getLayerContents().size());
    assertEquals(image1, model.getLayerContents().get("dog"));

    new Copy().applyCommand(model);

    assertEquals(2, model.getLayerContents().size());
    assertEquals(image1.duplicateImage(), model.getLayerContents().get("dog copy"));

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullCopyModel() {
    new Copy().applyCommand(null);
  }

  @Test
  public void testCreateLayerCommand() {

    assertEquals(0, model.getLayerContents().size());
    model.createLayer("cat");
    model.loadImage(image1);
    assertEquals(1, model.getLayerContents().size());

    new CreateLayer("dog").applyCommand(model);
    model.loadImage(image2);

    assertEquals(2, model.getLayerContents().size());
    assertEquals(image2, model.getLayerContents().get("dog"));

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullLayerName() {
    new CreateLayer(null).applyCommand(model);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullCreateLayerModel() {
    new CreateLayer("hi").applyCommand(null);
  }

  @Test
  public void testSaveAllCommand() {

    model.createLayer("dog");
    model.loadImage(image1);

    model.createLayer("cat");
    model.loadImage(image2);

    // make sure new file doesn't already exist
    File testFile = new File("animal-photos/dog.jpg");
    testFile.delete();
    File testFile1 = new File("animal-photos/cat.jpg");
    testFile1.delete();
    File testFile3 = new File("animal-photos/animal-photos.txt");
    testFile3.delete();
    File testFile2 = new File("animal-photos");
    testFile2.delete();

    new SaveAll("animal-photos", "jpg").applyCommand(model);


    Path path = null;
    Path path1 = null;
    Path path2 = null;
    try {
      path = Files.createTempDirectory("animal-photos");
      path1 = Files.createTempFile(path, "dog", ".jpg");
      path2 = Files.createTempFile(path, "cat", ".jpg");
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

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullName() {
    new SaveAll(null, "j").applyCommand(model);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullFileType() {
    new SaveAll("cool-photo", null).applyCommand(model);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullSaveAllModel() {
    new SaveAll("hi", "j").applyCommand(null);
  }

  @Test
  public void testSepiaCommand() {
    model.createLayer("dog");
    model.loadImage(image1);


    modelCopy.createLayer("dog");
    modelCopy.loadImage(image1Copy);

    assertEquals(model.getImage("dog"), modelCopy.getImage("dog"));

    // sepia one of the images and check that they are different now
    new SepiaCommand().applyCommand(model);
    assertNotEquals(model.getImage("dog"), modelCopy.getImage("dog"));

    // check that image actually got applied sepia
    assertEquals(new Sepia().applyFilter(image1Copy), model.getImage("dog"));

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullSepiaModel() {
    new SepiaCommand().applyCommand(null);
  }

  @Test
  public void testSharpenCommand() {
    model.createLayer("dog");
    model.loadImage(image1);


    modelCopy.createLayer("dog");
    modelCopy.loadImage(image1Copy);

    assertEquals(model.getImage("dog"), modelCopy.getImage("dog"));

    new SharpenCommand().applyCommand(model);

    // check that image actually got sharpened
    assertEquals(new Sharpen().applyFilter(image1Copy), model.getImage("dog"));

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullSharpenModel() {
    new SharpenCommand().applyCommand(null);
  }


  @Test
  public void testRemoveLayerCommand() {


    assertEquals(0, model.getLayerContents().size());

    model.createLayer("dog");
    model.loadImage(image1);

    assertEquals(1, model.getLayerContents().size());
    assertEquals(image1, model.getLayerContents().get("dog"));

    new RemoveLayer().applyCommand(model);
    assertEquals(0, model.getLayerContents().size());


  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullRemoveLayerModel() {
    new RemoveLayer().applyCommand(null);
  }

  @Test
  public void testRemoveImageCommand() {
    model.createLayer("dog");
    model.loadImage(image1);
    assertEquals(image1, model.getImage("dog"));

    model.createLayer("cat");
    model.loadImage(image2);
    assertEquals(image2, model.getImage("cat"));

    new RemoveImage().applyCommand(model);

    assertEquals(null, model.getImage("cat"));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullRemoveImageModel() {
    new RemoveImage().applyCommand(null);
  }

  @Test
  public void testVisibleCommand() {

    model.createLayer("dog");
    model.loadImage(image1);
    model.makeLayerInvisible();

    model.createLayer("cat");
    model.loadImage(image2);
    model.makeLayerInvisible();

    model.createLayer("fish");
    model.loadImage(image3);
    model.makeLayerInvisible();

    // now we have all 3 layers with images, but they are all invisible

    // goes to the middle layer and then makes it visible
    model.goToLayer("cat");
    new Visible().applyCommand(model);

    // now get the top most visible image which should be the image at
    // the cat layer because the other layers should be invisible
    assertEquals(image2, model.getVisibleImage());

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullVisibleModel() {
    new Visible().applyCommand(null);
  }

  @Test
  public void testCreateCommand() {

    model.createLayer("dog");
    model.loadImage(image1);

    model.createLayer("cat");
    model.loadImage(image2);

    model.createLayer("mouse");
    model.loadImage(image3);

    model.makeLayerInvisible(); //makes the topmost layer (mouse) invisible

    new Current("cat").applyCommand(model); // goes to layer below it

    model.makeLayerInvisible(); // now makes (cat) invisible

    // now calling get visible image should get the bottom layer
    // this also tests that goToLayer works correctly by
    // going to the layer and then makeLayerInvisible is called on it
    assertEquals(image1, model.getVisibleImage());

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullModelCurrent() {
    new Current("hi").applyCommand(null);
  }

  @Test
  public void testGreyscaleCommand() {
    model.createLayer("dog");
    model.loadImage(image1);


    modelCopy.createLayer("dog");
    modelCopy.loadImage(image1Copy);

    assertEquals(model.getImage("dog"), modelCopy.getImage("dog"));

    new GreyscaleCommand().applyCommand(model);

    // check that image actually got grey-scaled
    assertEquals(new Greyscale().applyFilter(image1Copy), model.getImage("dog"));

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullModelGreyscale() {
    new GreyscaleCommand().applyCommand(null);
  }

  @Test
  public void testInvisibleCommand() {
    model.createLayer("dog");
    model.loadImage(image1);

    model.createLayer("cat");
    model.loadImage(image2);

    new Invisible().applyCommand(model); //makes top invisible

    model.goToLayer("dog");

    // after getting the topmost visible image, it should actually
    // get the layer on the bottom because the top is invisible
    assertEquals(image1, model.getVisibleImage());

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullInvisibleModel() {
    new Invisible().applyCommand(null);
  }

  @Test
  public void testLoadCheckerboardCommand() {
    model.createLayer("dog");

    new LoadCheckerboard("4", "1", "1").applyCommand(model);

    assertEquals(image1, model.getImage("dog"));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullModelCheckerboard() {
    new LoadCheckerboard("1", "2", "3").applyCommand(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testHeightNull() {
    new LoadCheckerboard("1", "1", null).applyCommand(model);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testWidthNull() {
    new LoadCheckerboard("1", null, "1").applyCommand(model);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testTileSizeNull() {
    new LoadCheckerboard(null, "1", "1").applyCommand(model);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testModelNull() {
    new LoadCheckerboard("1", "1", "1").applyCommand(null);
  }


  @Test
  public void testLoadFileCommand() {

    model.createLayer("dog");

    new LoadFile("test/testppmimages/test1x1Checkerboard.ppm").applyCommand(model);

    assertEquals(image4, model.getImage("dog"));

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullPathStringLoadFile() {
    new LoadFile(null).applyCommand(model);
  }


  @Test (expected = IllegalArgumentException.class)
  public void testNullModelLoadFile() {
    new LoadFile("test/testppmimages/test1x1Checkerboard.ppm").applyCommand(null);
  }

  @Test
  public void testLoadProject() {

    listOfImages.put("1", image1);
    listOfImages.put("2", image2);
    listOfImages.put("3", image3);

    model.loadProject(listOfImages);

    assertEquals(image1, model.getImage("1"));
    assertEquals(image2, model.getImage("2"));
    assertEquals(image3, model.getImage("3"));



  }

  @Test
  public void testLoadProjectButSomethingThere() {

    model.createLayer("hi");
    model.loadImage(wrongDimensionsImage);

    listOfImages.put("1", image1);
    listOfImages.put("2", image2);
    listOfImages.put("3", image3);

    model.loadProject(listOfImages);

    assertEquals(image1, model.getImage("1"));
    assertEquals(image2, model.getImage("2"));
    assertEquals(image3, model.getImage("3"));

  }



  @Test (expected = IllegalArgumentException.class)
  public void testNullPathString() {
    new LoadProject(null).applyCommand(model);
  }


  @Test (expected = IllegalArgumentException.class)
  public void testNullModel() {
    new LoadProject("test/testppmimages/test1x1Checkerboard.ppm").applyCommand(null);
  }



}
