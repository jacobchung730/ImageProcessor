import model.CheckerboardImage;
import model.filter.Blur;
import model.filter.IFilter;
import model.IImage;
import model.Image;
import model.Pixel;
import model.Posn;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;


/**
 * Tests for the blur filter.
 */
public class BlurTest {

  private IFilter blur1 = new Blur();
  // checkerboard is a 2 pixel by 2 pixel checkerboard where each tile is 1 pixel large
  private IImage checkerboard = new CheckerboardImage(1, 2, 2).createCheckerboard();

  @Test
  public void testBlurSmallImage() {
    IImage newImage = this.blur1.applyFilter(this.checkerboard);
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

    assertEquals(resultImage, newImage);

    // blur again

    IImage newImage2 = this.blur1.applyFilter(newImage);
    List<List<Pixel>> result2 = new ArrayList<>();
    row1 = new ArrayList<>();
    row1.add(new Pixel(41, 41, 41, new Posn(0, 0)));
    row1.add(new Pixel(40, 40, 40, new Posn(1, 0)));
    row2 = new ArrayList<>();
    row2.add(new Pixel(40, 40, 40, new Posn(0, 1)));
    row2.add(new Pixel(41, 41, 41, new Posn(1, 1)));
    result2.add(row1);
    result2.add(row2);
    IImage resultImage2 = new Image(result2, 255);

    assertEquals(resultImage2, newImage2);
  }


  @Test
  public void test3x3PixelImage() {
    IImage newImage = this.blur1.applyFilter(new Image("test/TestPPMImages/LowRes.ppm"));
    List<List<Pixel>> result = new ArrayList<>();

    List<Pixel> row1 = new ArrayList<>();
    row1.add(new Pixel(56, 56, 56, new Posn(0, 0)));
    row1.add(new Pixel(75, 75, 75, new Posn(1, 0)));
    row1.add(new Pixel(56, 56, 56, new Posn(2, 0)));

    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(75, 75, 75, new Posn(0, 1)));
    row2.add(new Pixel(100, 100, 100, new Posn(1, 1)));
    row2.add(new Pixel(75, 75, 75, new Posn(2, 1)));

    List<Pixel> row3 = new ArrayList<>();
    row3.add(new Pixel(56, 56, 56, new Posn(0, 2)));
    row3.add(new Pixel(75, 75, 75, new Posn(1, 2)));
    row3.add(new Pixel(56, 56, 56, new Posn(2, 2)));

    result.add(row1);
    result.add(row2);
    result.add(row3);
    IImage resultImage = new Image(result, 255);

    assertEquals(resultImage, newImage);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImage() {
    this.blur1.applyFilter(null);
  }


  // blurring this image will cause all of the values to go over the max value
  // of this image which we have set to 10 for this test
  @Test
  public void testBlurHitsMaxValue() {
    List<List<Pixel>> initial = new ArrayList<>();

    List<Pixel> row1 = new ArrayList<>();
    row1.add(new Pixel(250, 250, 250, new Posn(0, 0)));
    row1.add(new Pixel(250, 250, 250, new Posn(1, 0)));
    row1.add(new Pixel(250, 250, 250, new Posn(2, 0)));

    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(250, 250, 250, new Posn(0, 1)));
    row2.add(new Pixel(250, 250, 250, new Posn(1, 1)));
    row2.add(new Pixel(250, 250, 250, new Posn(2, 1)));

    initial.add(row1);
    initial.add(row2);
    IImage initialImage = new Image(initial, 10);


    List<List<Pixel>> answer = new ArrayList<>();

    List<Pixel> row01 = new ArrayList<>();
    row01.add(new Pixel(10, 10, 10, new Posn(0, 0)));
    row01.add(new Pixel(10, 10, 10, new Posn(1, 0)));
    row01.add(new Pixel(10, 10, 10, new Posn(2, 0)));

    List<Pixel> row02 = new ArrayList<>();
    row02.add(new Pixel(10, 10, 10, new Posn(0, 1)));
    row02.add(new Pixel(10, 10, 10, new Posn(1, 1)));
    row02.add(new Pixel(10, 10, 10, new Posn(2, 1)));

    answer.add(row01);
    answer.add(row02);
    IImage answerImage = new Image(answer, 10);

    assertEquals(blur1.applyFilter(initialImage), answerImage);

  }

  @Test
  public void testBlurOnAllBlackImage() {
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

    IImage newImage = this.blur1.applyFilter(originalImage);

    assertEquals(originalImage, newImage);
  }


}
