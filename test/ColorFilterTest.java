import invalidfilters.FakeColorFilter;
import org.junit.Test;

/**
 * Tests for the ColorFilter abstract class.
 */
public class ColorFilterTest {

  @Test (expected = IllegalArgumentException.class)
  public void testMatrixTooManyRows() {
    new FakeColorFilter(new double[4][2]);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMatrixTooFewRows() {
    new FakeColorFilter(new double[2][3]);
  }


}
