import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.LinkedHashMap;
import java.util.Map;
import model.CheckerboardImage;
import model.IImage;
import model.ILayeredImageModel;
import model.LayeredImageModel;
import model.filter.Blur;
import org.junit.Test;

/**
 * Tests for the LayeredImageModel class.
 */
public class LayeredImageModelTest {

  private ILayeredImageModel model = new LayeredImageModel();
  private ILayeredImageModel modelCopy = new LayeredImageModel();
  private IImage image1 = new CheckerboardImage(4, 1, 1).createCheckerboard();
  private IImage image1Copy = new CheckerboardImage(4, 1, 1).createCheckerboard();
  private IImage image2 = new CheckerboardImage(2, 2, 2).createCheckerboard();
  private IImage image3 = new CheckerboardImage(1, 4, 4).createCheckerboard();
  private IImage wrongDimensionsImage = new CheckerboardImage(10, 10, 10).createCheckerboard();

  private Map<String, IImage> listOfImages = new LinkedHashMap<>();



  @Test (expected = IllegalArgumentException.class)
  public void testConstructorNull() {
    new LayeredImageModel(null);
  }


  @Test
  public void testCreateLayer() {
    assertEquals(0, model.getLayerContents().size());
    model.createLayer("dog");
    assertEquals(1, model.getLayerContents().size());
    assertEquals(null, model.getLayerContents().get("dog"));
  }

  @Test
  public void testCreateLayerCopyWithoutImage() {
    assertEquals(0, model.getLayerContents().size());
    model.createLayer("dog");
    assertEquals(1, model.getLayerContents().size());
    assertEquals(null, model.getLayerContents().get("dog"));
    model.createLayerCopy();
    assertEquals(2, model.getLayerContents().size());
    assertEquals(null, model.getLayerContents().get("dog copy"));
  }

  @Test
  public void testCreateLayerCopyWithAnImage() {
    assertEquals(0, model.getLayerContents().size());
    model.createLayer("dog");
    model.loadImage(image1);
    assertEquals(1, model.getLayerContents().size());
    assertEquals(image1, model.getLayerContents().get("dog"));
    model.createLayerCopy();
    assertEquals(2, model.getLayerContents().size());
    assertEquals(image1.duplicateImage(), model.getLayerContents().get("dog copy"));
  }


  @Test
  public void testRemoveLayer() {
    assertEquals(0, model.getLayerContents().size());

    model.createLayer("dog");
    model.loadImage(image1);

    assertEquals(1, model.getLayerContents().size());
    assertEquals(image1, model.getLayerContents().get("dog"));

    model.removeLayer();
    assertEquals(0, model.getLayerContents().size());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveLayerButNoLayers() {
    model.removeLayer();
  }



  @Test
  public void testGoToLayer() {
    model.createLayer("dog");
    model.loadImage(image1);

    model.createLayer("cat");
    model.loadImage(image2);

    model.createLayer("mouse");
    model.loadImage(image3);

    model.makeLayerInvisible(); //makes the topmost layer (mouse) invisible

    model.goToLayer("cat"); // goes to layer below it

    model.makeLayerInvisible(); // now makes (cat) invisible

    // now calling get visible image should get the bottom layer
    // this also tests that goToLayer works correctly by
    // going to the layer and then makeLayerInvisible is called on it
    assertEquals(image1, model.getVisibleImage());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoToLayerButDoesNotExist() {
    model.goToLayer("hi");
  }

  @Test
  public void testMakeLayerInvisible() {
    model.createLayer("dog");
    model.loadImage(image1);

    model.createLayer("cat");
    model.loadImage(image2);

    model.makeLayerInvisible(); //makes top invisible

    model.goToLayer("dog");

    // after getting the topmost visible image, it should actually
    // get the layer on the bottom because the top is invisible
    assertEquals(image1, model.getVisibleImage());

  }

  @Test
  public void testMakeLayerVisible() {

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
    model.makeLayerVisible();

    // now get the top most visible image which should be the image at
    // the cat layer because the other layers should be invisible
    assertEquals(image2, model.getVisibleImage());


  }

  @Test
  public void testLoadImage() {
    assertEquals(0, model.getLayerContents().size());
    model.createLayer("dog");
    assertEquals(1, model.getLayerContents().size());
    assertEquals(null, model.getLayerContents().get("dog"));
    model.loadImage(image1);
    assertEquals(image1, model.getLayerContents().get("dog"));
  }

  @Test
  public void testLoadImageOntoImage() {
    assertEquals(0, model.getLayerContents().size());
    model.createLayer("dog");
    assertEquals(1, model.getLayerContents().size());
    assertEquals(null, model.getLayerContents().get("dog"));
    model.loadImage(image1);
    assertEquals(image1, model.getLayerContents().get("dog"));
    model.loadImage(image2);
    assertEquals(image2, model.getLayerContents().get("dog"));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadImageNull() {
    model.loadImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadImageButNotSameDimensions() {
    model.createLayer("dog");
    model.loadImage(image1);
    model.createLayer("cat");
    model.loadImage(wrongDimensionsImage);
    //image2 has different dimensions that the image
    // at the layer below
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
    model.loadImage(image1);

    listOfImages.put("1", image2);
    listOfImages.put("2", image3);


    model.loadProject(listOfImages);

    assertEquals(image2, model.getImage("1"));
    assertEquals(image3, model.getImage("2"));



  }



  @Test
  public void testRemoveImage() {

    model.createLayer("dog");
    model.loadImage(image1);
    assertEquals(image1, model.getImage("dog"));

    model.createLayer("cat");
    model.loadImage(image2);
    assertEquals(image2, model.getImage("cat"));

    model.removeImage();
    assertEquals(null, model.getImage("cat"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveImageNoLayers() {
    model.removeImage();
  }

  @Test
  public void testApplyFilter() {

    model.createLayer("dog");
    model.loadImage(image1);


    modelCopy.createLayer("dog");
    modelCopy.loadImage(image1Copy);

    assertEquals(model.getImage("dog"), modelCopy.getImage("dog"));

    // blur one of the images and check that they are different now
    model.applyFilter(new Blur());
    assertNotEquals(model.getImage("dog"), modelCopy.getImage("dog"));

    // check that image actually got blurred
    assertEquals(new Blur().applyFilter(image1Copy), model.getImage("dog"));

  }

  @Test
  public void testGetImage() {
    model.createLayer("dog");
    model.loadImage(image1);
    assertEquals(image1, model.getImage("dog"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetImageNull() {
    model.createLayer("dog");
    model.loadImage(image1);
    model.getImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetImageButInvalidKey() {
    model.createLayer("dog");
    model.loadImage(image1);
    model.getImage("this is not a valid key");
  }

  @Test
  public void testGetVisibleImage() {
    model.createLayer("dog");
    model.loadImage(image1);
    model.createLayer("cat");
    model.loadImage(image2);
    model.makeLayerInvisible();
    assertEquals(image1, model.getVisibleImage());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetVisibleImageButAllInvisible() {
    model.createLayer("dog");
    model.loadImage(image1);
    model.makeLayerInvisible();
    model.getVisibleImage();
  }

  @Test
  public void testGetLayerContents() {
    assertEquals(listOfImages, model.getLayerContents());
  }








}
