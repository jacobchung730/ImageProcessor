import java.util.ArrayList;
import java.util.List;
import model.CheckerboardImage;
import model.IImage;
import model.Image;
import model.Pixel;
import model.Posn;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Tests for the Image class.
 */
public class ImageTest {

  private IImage image1 = new CheckerboardImage(1, 2, 2).createCheckerboard();
  private IImage image2 = new Image(buildCustomImageGrid(), 200);
  private IImage image3 = new CheckerboardImage(1, 2, 2).createCheckerboard();


  private List<List<Pixel>> buildCustomImageGrid() {
    List<List<Pixel>> expected = new ArrayList<>();
    List<Pixel> row1 = new ArrayList<>();
    row1.add(new Pixel(120, 120, 200, new Posn(0, 0)));
    row1.add(new Pixel(45, 0, 45, new Posn(1, 0)));
    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(45, 0, 0, new Posn(0, 1)));
    row2.add(new Pixel(200, 200, 200, new Posn(1, 1)));
    expected.add(row1);
    expected.add(row2);
    return expected;
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegativeMaxValue() {
    new Image(buildCustomImageGrid(), -25);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullGrid() {
    new Image(null, 25);
  }

  @Test
  public void testGetGrid() {
    List<List<Pixel>> expected = new ArrayList<>();
    List<Pixel> row1 = new ArrayList<>();
    row1.add(new Pixel(255, 255, 255, new Posn(0, 0)));
    row1.add(new Pixel(0, 0, 0, new Posn(1, 0)));
    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(0, 0, 0, new Posn(0, 1)));
    row2.add(new Pixel(255, 255, 255, new Posn(1, 1)));
    expected.add(row1);
    expected.add(row2);
    assertEquals(expected, image1.getGrid());

    assertEquals(buildCustomImageGrid(), image2.getGrid());
  }

  @Test
  public void testGetMaxValue() {
    assertEquals(255, image1.getMaxValue());
    assertEquals(200, image2.getMaxValue());
  }

  @Test
  public void testImageEquals() {
    assertTrue(image1.equals(image3));
    assertFalse(image1.equals(image2));
    assertFalse(image3.equals(image2));
  }

  @Test
  public void testImageHashCode() {
    assertEquals(image1.hashCode(), image3.hashCode());
    assertNotEquals(image1.hashCode(), image2.hashCode());
    assertNotEquals(image3.hashCode(), image2.hashCode());
  }
}
