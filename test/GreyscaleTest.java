import java.util.ArrayList;
import java.util.List;
import model.CheckerboardImage;
import model.IImage;
import model.Image;
import model.Pixel;
import model.Posn;
import model.filter.Greyscale;
import model.filter.IFilter;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the grey scale filter.
 */
public class GreyscaleTest {

  private IFilter greyscale = new Greyscale();
  private IImage checkerboard = new CheckerboardImage(1, 3, 3).createCheckerboard();
  private IImage colorImage = new Image(buildCustomImageGrid(), 255);

  private List<List<Pixel>> buildCustomImageGrid() {
    List<List<Pixel>> expected = new ArrayList<>();
    List<Pixel> row1 = new ArrayList<>();
    row1.add(new Pixel(120, 120, 200, new Posn(0, 0)));
    row1.add(new Pixel(45, 0, 45, new Posn(1, 0)));
    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(45, 0, 45, new Posn(0, 1)));
    row2.add(new Pixel(120, 120, 200, new Posn(1, 1)));
    expected.add(row1);
    expected.add(row2);
    return expected;
  }

  // a checkerboard is already in grey scale, so applying this filter does nothing
  @Test
  public void testGreyCheckerBoard() {
    IImage greyChecker = this.greyscale.applyFilter(this.checkerboard);
    List<List<Pixel>> grid = new ArrayList<>();
    List<Pixel> row1 = new ArrayList<>();
    List<Pixel> row2 = new ArrayList<>();
    List<Pixel> row3 = new ArrayList<>();

    row1.add(new Pixel(255, 255, 255, new Posn(0, 0)));
    row1.add(new Pixel(0, 0, 0, new Posn(1, 0)));
    row1.add(new Pixel(255, 255, 255, new Posn(2, 0)));

    row2.add(new Pixel(0, 0, 0, new Posn(0, 1)));
    row2.add(new Pixel(255, 255, 255, new Posn(1, 1)));
    row2.add(new Pixel(0, 0, 0, new Posn(2, 1)));

    row3.add(new Pixel(255, 255, 255, new Posn(0, 2)));
    row3.add(new Pixel(0, 0, 0, new Posn(1, 2)));
    row3.add(new Pixel(255, 255, 255, new Posn(2, 2)));

    grid.add(row1);
    grid.add(row2);
    grid.add(row3);

    assertEquals(new Image(grid, 255), greyChecker);
  }

  @Test
  public void testGreyScaleColorImage() {
    IImage greyImage = this.greyscale.applyFilter(this.colorImage);

    List<List<Pixel>> expected = new ArrayList<>();
    List<Pixel> row1 = new ArrayList<>();
    row1.add(new Pixel(126, 126, 126, new Posn(0, 0)));
    row1.add(new Pixel(13, 13, 13, new Posn(1, 0)));
    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(13, 13, 13, new Posn(0, 1)));
    row2.add(new Pixel(126, 126, 126, new Posn(1, 1)));
    expected.add(row1);
    expected.add(row2);

    assertEquals(new Image(expected, 255), greyImage);

    // greyscale same image again, nothing happens

    IImage greyImage2 = this.greyscale.applyFilter(greyImage);

    List<List<Pixel>> expected2 = new ArrayList<>();
    row1 = new ArrayList<>();
    row1.add(new Pixel(126, 126, 126, new Posn(0, 0)));
    row1.add(new Pixel(13, 13, 13, new Posn(1, 0)));
    row2 = new ArrayList<>();
    row2.add(new Pixel(13, 13, 13, new Posn(0, 1)));
    row2.add(new Pixel(126, 126, 126, new Posn(1, 1)));
    expected2.add(row1);
    expected2.add(row2);

    assertEquals(new Image(expected2, 255), greyImage2);


  }

  // make sure greyscale will cap rgb values
  @Test
  public void testGreyScaleHitsMaxValue() {
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

    assertEquals(greyscale.applyFilter(initialImage), answerImage);

  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImage() {
    this.greyscale.applyFilter(null);
  }


}
