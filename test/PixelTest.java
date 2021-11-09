import static org.junit.Assert.assertEquals;

import model.Pixel;
import model.Posn;
import org.junit.Test;

/**
 * Tests for the Pixel class.
 */
public class PixelTest {

  private Pixel pix = new Pixel(10, 11, 12, new Posn(2, 3));
  private Pixel pix2 = new Pixel(10, 11, 12, new Posn(2, 3));

  @Test
  public void testPixelHashCode() {
    assertEquals(pix, pix2);
  }


  @Test
  public void testPixelGetX() {
    assertEquals(2, pix.getX());
  }

  @Test
  public void testPixelGetY() {
    assertEquals(3, pix.getY());
  }

  @Test
  public void testPixelGetRed() {
    assertEquals(10, pix.getRedValue());
  }

  @Test
  public void testPixelGetGreen() {
    assertEquals(11, pix.getGreenValue());
  }

  @Test
  public void testPixelGetBlue() {
    assertEquals(12, pix.getBlueValue());
  }

  @Test
  public void testPixelEquals() {
    Pixel p1 = new Pixel(0, 0, 0, new Posn(1,1));
    Pixel p2 = new Pixel(0, 0, 0, new Posn(1, 1));

    assertEquals(p1, p2);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testPixelRedColorIsAbove255() {
    new Pixel(256, 5, 5, new Posn(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPixelGreenColorIsAbove255() {
    new Pixel(5, 256, 5, new Posn(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPixelBlueColorIsAbove255() {
    new Pixel(5, 5, 256, new Posn(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPixelRedColorIsBelow0() {
    new Pixel(-5, 5, 5, new Posn(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPixelGreenColorIsBelow0() {
    new Pixel(5, -5, 5, new Posn(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPixelBlueColorIsBelow0() {
    new Pixel(5, 5, -5, new Posn(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPixelPositionNull() {
    new Pixel(5, 5, 5,null);
  }




}
