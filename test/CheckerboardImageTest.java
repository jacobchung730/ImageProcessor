import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import model.CheckerboardImage;
import model.Image;
import model.Pixel;
import model.Posn;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the CheckerBoardImage class. Tests creating checker boards.
 */
public class CheckerboardImageTest {

  private CheckerboardImage image1;
  private CheckerboardImage image2;
  private CheckerboardImage image3;
  private CheckerboardImage image4;

  @Before
  public void init() {
    this.image1 = new CheckerboardImage(1, 3, 3);
    this.image2 = new CheckerboardImage(1, 3, 2);
    this.image3 = new CheckerboardImage(2, 2, 2);
    this.image4 = new CheckerboardImage(2, 2, 3);
  }

  @Test
  public void testSquareCheckerboard() {
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

    Image checker =  (Image) this.image1.createCheckerboard();
    Image main =  new Image(grid, 255);


    for (int i = 0; i < main.getGrid().size(); i++) {
      List<Pixel> l = main.getGrid().get(i);
      List<Pixel> l2 = checker.getGrid().get(i);
      for (int j = 0; j < l.size(); j++) {
        assertEquals(l.get(j), l2.get(j));
      }
    }

    assertEquals(new Image(grid, 255), this.image1.createCheckerboard());

  }

  @Test
  public void testNonSquareCheckerboard() {
    List<List<Pixel>> grid = new ArrayList<>();
    List<Pixel> row1 = new ArrayList<>();
    List<Pixel> row2 = new ArrayList<>();

    row1.add(new Pixel(255, 255, 255, new Posn(0, 0)));
    row1.add(new Pixel(0, 0, 0, new Posn(1, 0)));
    row1.add(new Pixel(255, 255, 255, new Posn(2, 0)));

    row2.add(new Pixel(0, 0, 0, new Posn(0, 1)));
    row2.add(new Pixel(255, 255, 255, new Posn(1, 1)));
    row2.add(new Pixel(0, 0, 0, new Posn(2, 1)));

    grid.add(row1);
    grid.add(row2);

    Image actual = (Image) this.image2.createCheckerboard();
    Image expected =  new Image(grid, 255);


    for (int i = 0; i < expected.getGrid().size(); i++) {
      List<Pixel> l = expected.getGrid().get(i);
      List<Pixel> l2 = actual.getGrid().get(i);
      for (int j = 0; j < l.size(); j++) {
        assertEquals(l.get(j), l2.get(j));
      }
    }

    assertEquals(expected, actual);
  }

  @Test
  public void testSquareCheckerBoardTileSize2() {
    List<List<Pixel>> grid = new ArrayList<>();
    List<Pixel> row1 = new ArrayList<>();
    List<Pixel> row2 = new ArrayList<>();
    List<Pixel> row3 = new ArrayList<>();
    List<Pixel> row4 = new ArrayList<>();

    row1.add(new Pixel(255, 255, 255, new Posn(0, 0)));
    row1.add(new Pixel(255, 255, 255, new Posn(1, 0)));
    row1.add(new Pixel(0, 0, 0, new Posn(2, 0)));
    row1.add(new Pixel(0, 0, 0, new Posn(3, 0)));

    row2.add(new Pixel(255, 255, 255, new Posn(0, 1)));
    row2.add(new Pixel(255, 255, 255, new Posn(1, 1)));
    row2.add(new Pixel(0, 0, 0, new Posn(2, 1)));
    row2.add(new Pixel(0, 0, 0, new Posn(3, 1)));

    row3.add(new Pixel(0, 0, 0, new Posn(0, 2)));
    row3.add(new Pixel(0, 0, 0, new Posn(1, 2)));
    row3.add(new Pixel(255, 255, 255, new Posn(2, 2)));
    row3.add(new Pixel(255, 255, 255, new Posn(3, 2)));

    row4.add(new Pixel(0, 0, 0, new Posn(0, 3)));
    row4.add(new Pixel(0, 0, 0, new Posn(1, 3)));
    row4.add(new Pixel(255, 255, 255, new Posn(2, 3)));
    row4.add(new Pixel(255, 255, 255, new Posn(3, 3)));


    grid.add(row1);
    grid.add(row2);
    grid.add(row3);
    grid.add(row4);

    Image checker =  (Image) this.image3.createCheckerboard();
    Image expected =  new Image(grid, 255);


    for (int i = 0; i < expected.getGrid().size(); i++) {
      List<Pixel> l = expected.getGrid().get(i);
      List<Pixel> l2 = checker.getGrid().get(i);
      for (int j = 0; j < l.size(); j++) {
        assertEquals(l.get(j), l2.get(j));
      }
    }

    assertEquals(expected, checker);
  }

  @Test
  public void test2x3CheckerBoardTileSize2() {
    List<List<Pixel>> grid = new ArrayList<>();
    List<Pixel> row1 = new ArrayList<>();
    List<Pixel> row2 = new ArrayList<>();
    List<Pixel> row3 = new ArrayList<>();
    List<Pixel> row4 = new ArrayList<>();
    List<Pixel> row5 = new ArrayList<>();
    List<Pixel> row6 = new ArrayList<>();

    row1.add(new Pixel(255, 255, 255, new Posn(0, 0)));
    row1.add(new Pixel(255, 255, 255, new Posn(1, 0)));
    row1.add(new Pixel(0, 0, 0, new Posn(2, 0)));
    row1.add(new Pixel(0, 0, 0, new Posn(3, 0)));

    row2.add(new Pixel(255, 255, 255, new Posn(0, 1)));
    row2.add(new Pixel(255, 255, 255, new Posn(1, 1)));
    row2.add(new Pixel(0, 0, 0, new Posn(2, 1)));
    row2.add(new Pixel(0, 0, 0, new Posn(3, 1)));

    row3.add(new Pixel(0, 0, 0, new Posn(0, 2)));
    row3.add(new Pixel(0, 0, 0, new Posn(1, 2)));
    row3.add(new Pixel(255, 255, 255, new Posn(2, 2)));
    row3.add(new Pixel(255, 255, 255, new Posn(3, 2)));

    row4.add(new Pixel(0, 0, 0, new Posn(0, 3)));
    row4.add(new Pixel(0, 0, 0, new Posn(1, 3)));
    row4.add(new Pixel(255, 255, 255, new Posn(2, 3)));
    row4.add(new Pixel(255, 255, 255, new Posn(3, 3)));


    row5.add(new Pixel(255, 255, 255, new Posn(0, 4)));
    row5.add(new Pixel(255, 255, 255, new Posn(1, 4)));
    row5.add(new Pixel(0, 0, 0, new Posn(2, 4)));
    row5.add(new Pixel(0, 0, 0, new Posn(3, 4)));

    row6.add(new Pixel(255, 255, 255, new Posn(0, 5)));
    row6.add(new Pixel(255, 255, 255, new Posn(1, 5)));
    row6.add(new Pixel(0, 0, 0, new Posn(2, 5)));
    row6.add(new Pixel(0, 0, 0, new Posn(3, 5)));


    grid.add(row1);
    grid.add(row2);
    grid.add(row3);
    grid.add(row4);
    grid.add(row5);
    grid.add(row6);

    Image checker =  (Image) this.image4.createCheckerboard();
    Image expected =  new Image(grid, 255);


    for (int i = 0; i < expected.getGrid().size(); i++) {
      List<Pixel> l = expected.getGrid().get(i);
      List<Pixel> l2 = checker.getGrid().get(i);
      for (int j = 0; j < l.size(); j++) {
        assertEquals(l.get(j), l2.get(j));
      }
    }

    assertEquals(expected, checker);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegativeTileSize() {
    new CheckerboardImage(-1, 3, 3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegativeWidth() {
    new CheckerboardImage(1, -3, 3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegativeHeight() {
    new CheckerboardImage(1, 3, -3);
  }







}
