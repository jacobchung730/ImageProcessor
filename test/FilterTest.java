import invalidfilters.FakeFilter;
import org.junit.Test;

/**
 * Tests for the Filter abstract class.
 */
public class FilterTest {

  @Test (expected = IllegalArgumentException.class)
  public void testEvenKernel() {
    new FakeFilter(new double[2][2]);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNonSquareKernel() {
    new FakeFilter(new double[3][2]);
  }


}
