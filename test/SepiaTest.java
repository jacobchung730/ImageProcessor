import java.util.ArrayList;
import java.util.List;
import model.CheckerboardImage;
import model.filter.IFilter;
import model.filter.Sepia;
import model.IImage;
import model.Image;
import model.Pixel;
import model.Posn;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the sepia filter.
 */
public class SepiaTest {

  private IFilter sepia1 = new Sepia();
  private IImage checkerboard = new CheckerboardImage(1, 2, 2).createCheckerboard();


  @Test
  public void testSepiaSmallCheckerboard() {
    IImage newImage = this.sepia1.applyFilter(this.checkerboard);
    List<List<Pixel>> result = new ArrayList<>();
    List<Pixel> row1 = new ArrayList<>();
    // the red and green values here are clamped to 255
    row1.add(new Pixel(255, 255, 239, new Posn(0, 0)));
    row1.add(new Pixel(0, 0, 0, new Posn(1, 0)));
    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(0, 0, 0, new Posn(0, 1)));
    // the red and green values here are clamped to 255
    row2.add(new Pixel(255, 255, 239, new Posn(1, 1)));
    result.add(row1);
    result.add(row2);
    IImage resultImage = new Image(result, 255);

    assertEquals(resultImage, newImage);

    //apply sepia again, no change
    IImage newImage2 = this.sepia1.applyFilter(this.checkerboard);
    List<List<Pixel>> result2 = new ArrayList<>();
    row1 = new ArrayList<>();
    row1.add(new Pixel(255, 255, 239, new Posn(0, 0)));
    row1.add(new Pixel(0, 0, 0, new Posn(1, 0)));
    row2 = new ArrayList<>();
    row2.add(new Pixel(0, 0, 0, new Posn(0, 1)));
    row2.add(new Pixel(255, 255, 239, new Posn(1, 1)));
    result2.add(row1);
    result2.add(row2);
    IImage resultImage2 = new Image(result, 255);

    assertEquals(resultImage2, newImage2);

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImage() {
    this.sepia1.applyFilter(null);
  }

  @Test
  public void test3x3PixelImage() {
    IImage newImage = this.sepia1.applyFilter(new Image("test/TestPPMImages/LowRes.ppm"));
    List<List<Pixel>> result = new ArrayList<>();

    List<Pixel> row1 = new ArrayList<>();
    row1.add(new Pixel(135, 120, 94, new Posn(0, 0)));
    row1.add(new Pixel(135, 120, 94, new Posn(1, 0)));
    row1.add(new Pixel(135, 120, 94, new Posn(2, 0)));

    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(135, 120, 94, new Posn(0, 1)));
    row2.add(new Pixel(135, 120, 94, new Posn(1, 1)));
    row2.add(new Pixel(135, 120, 94, new Posn(2, 1)));

    List<Pixel> row3 = new ArrayList<>();
    row3.add(new Pixel(135, 120, 94, new Posn(0, 2)));
    row3.add(new Pixel(135, 120, 94, new Posn(1, 2)));
    row3.add(new Pixel(135, 120, 94, new Posn(2, 2)));

    result.add(row1);
    result.add(row2);
    result.add(row3);
    IImage resultImage = new Image(result, 255);

    assertEquals(resultImage, newImage);
  }

  @Test
  public void testSepiaOnAllBlackImage() {
    List<List<Pixel>> original = new ArrayList<>();

    List<Pixel> row1 = new ArrayList<>();
    row1.add(new Pixel(0, 0, 0, new Posn(0, 0)));
    row1.add(new Pixel(0, 0, 0, new Posn(1, 0)));

    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(0, 0, 0, new Posn(0, 1)));
    row2.add(new Pixel(0, 0, 0, new Posn(1, 1)));

    original.add(row1);
    original.add(row2);
    IImage originalImage = new Image(original, 255);

    IImage newImage = this.sepia1.applyFilter(originalImage);

    assertEquals(originalImage, newImage);
  }


}
