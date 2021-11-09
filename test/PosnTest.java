import static org.junit.Assert.assertEquals;

import model.Posn;
import org.junit.Test;

/**
 * Tests for the Posn test.
 */
public class PosnTest {

  private Posn posn1 = new Posn(1, 2);
  private Posn posn2 = new Posn(1, 2);

  @Test
  public void testPosnEquals() {
    assertEquals(posn1, posn2);
  }

  @Test
  public void testPosnHashCode() {
    assertEquals(posn1.hashCode(), posn2.hashCode());
  }

  @Test
  public void testPosnGetX() {
    assertEquals(1, posn1.getX());
  }

  @Test
  public void testPosnGetY() {
    assertEquals(2, posn1.getY());
  }

}
