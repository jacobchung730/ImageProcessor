import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import model.CheckerboardImage;
import model.filter.Blur;
import model.filter.Sepia;
import model.IImage;
import model.Image;
import model.SimpleImageModel;
import model.Pixel;
import model.Posn;
import org.junit.Test;

/**
 * Tests for the Model class.
 */
public class ModelTest {


  private SimpleImageModel model = new SimpleImageModel();
  private IImage checkerboard1 = new CheckerboardImage(10, 5, 5).createCheckerboard();
  private IImage checkerboard2 = new CheckerboardImage(2, 20, 20).createCheckerboard();
  private IImage checkerboard3 = new CheckerboardImage(1, 2, 2).createCheckerboard();


  @Test(expected = IllegalArgumentException.class)
  public void testModelAddImage() {
    model.addImage("first!", checkerboard1);
    model.getImage("first");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelRemoveImage() {

    model.addImage("my first photo", checkerboard1);
    model.removeImage("my first photo");
    model.getImage("my first photo");

  }

  @Test
  public void testModelReplaceImage() {
    model.addImage("my favorite photo", checkerboard2);

    assertEquals(checkerboard2, model.getImage("my favorite photo"));


    model.replaceImage("my favorite photo", checkerboard3);

    assertEquals(checkerboard3, model.getImage("my favorite photo"));

  }

  @Test
  public void testModelGetImage() {
    model.addImage("a very cool image", checkerboard1);
    assertEquals(checkerboard1, model.getImage("a very cool image"));

  }


  @Test
  public void testModelApplyFilter() {

    // creating the blurred image
    List<List<Pixel>> result = new ArrayList<>();
    List<Pixel> row1 = new ArrayList<>();
    row1.add(new Pixel(80, 80, 80, new Posn(0, 0)));
    row1.add(new Pixel(64, 64, 64, new Posn(1, 0)));
    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(64, 64, 64, new Posn(0, 1)));
    row2.add(new Pixel(80, 80, 80, new Posn(1, 1)));
    result.add(row1);
    result.add(row2);
    IImage resultImage = new Image(result, 255);

    model.addImage("123", checkerboard3);

    // check that checkerboard3 is actually at the key "123"
    assertEquals(checkerboard3, model.getImage("123"));

    // check that applying the given filter to the image at the
    // given string id will result in the resultImage
    assertEquals(resultImage, model.applyFilter("123", new Blur()));

  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddImageIdNull() {
    model.addImage(null, checkerboard1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddImageIsNull() {
    model.addImage("hi", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveImageNull() {
    model.removeImage(null);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testReplaceImageIsNull() {
    model.replaceImage("hi", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReplaceImageIdNull() {
    model.replaceImage(null, checkerboard1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetImageNull() {
    model.getImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyFilterIdNull() {
    model.applyFilter(null, new Sepia());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyFilterIsNull() {
    model.applyFilter("hello", null);
  }








}
