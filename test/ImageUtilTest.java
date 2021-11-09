import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import model.CheckerboardImage;
import model.ExportType;
import model.IImage;
import model.Image;
import controller.ImageUtil;
import model.Pixel;
import model.Posn;
import org.junit.Test;

/**
 * Tests for the ImageUtil class.
 */
public class ImageUtilTest {

  private IImage checkerboard = new CheckerboardImage(1, 2, 2).createCheckerboard();


  @Test
  public void testReadPPM() {
    List<List<Pixel>> resultListOfPixels = new ArrayList<>();

    List<Pixel> row1 = new ArrayList<>();
    row1.add(new Pixel(100, 100, 100, new Posn(0, 0)));
    row1.add(new Pixel(100, 100, 100, new Posn(1, 0)));
    row1.add(new Pixel(100, 100, 100, new Posn(2, 0)));

    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(100, 100, 100, new Posn(0, 1)));
    row2.add(new Pixel(100, 100, 100, new Posn(1, 1)));
    row2.add(new Pixel(100, 100, 100, new Posn(2, 1)));

    List<Pixel> row3 = new ArrayList<>();
    row3.add(new Pixel(100, 100, 100, new Posn(0, 2)));
    row3.add(new Pixel(100, 100, 100, new Posn(1, 2)));
    row3.add(new Pixel(100, 100, 100, new Posn(2, 2)));

    resultListOfPixels.add(row1);
    resultListOfPixels.add(row2);
    resultListOfPixels.add(row3);

    Image i = ImageUtil.readFile("test/TestPPMImages/LowRes.ppm");

    assertEquals(new Image(resultListOfPixels, 255), i);
  }

  @Test
  public void testReadPNG() {
    List<List<Pixel>> resultListOfPixels = new ArrayList<>();

    List<Pixel> row1 = new ArrayList<>();
    row1.add(new Pixel(100, 100, 100, new Posn(0, 0)));
    row1.add(new Pixel(100, 100, 100, new Posn(1, 0)));
    row1.add(new Pixel(100, 100, 100, new Posn(2, 0)));

    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(100, 100, 100, new Posn(0, 1)));
    row2.add(new Pixel(100, 100, 100, new Posn(1, 1)));
    row2.add(new Pixel(100, 100, 100, new Posn(2, 1)));

    List<Pixel> row3 = new ArrayList<>();
    row3.add(new Pixel(100, 100, 100, new Posn(0, 2)));
    row3.add(new Pixel(100, 100, 100, new Posn(1, 2)));
    row3.add(new Pixel(100, 100, 100, new Posn(2, 2)));

    resultListOfPixels.add(row1);
    resultListOfPixels.add(row2);
    resultListOfPixels.add(row3);

    Image i = ImageUtil.readFile("test/TestPPMImages/LowResCopy.png");

    assertEquals(new Image(resultListOfPixels, 255), i);
  }

  @Test
  public void testReadJPG() {
    List<List<Pixel>> resultListOfPixels = new ArrayList<>();

    List<Pixel> row1 = new ArrayList<>();
    row1.add(new Pixel(100, 100, 100, new Posn(0, 0)));
    row1.add(new Pixel(100, 100, 100, new Posn(1, 0)));
    row1.add(new Pixel(100, 100, 100, new Posn(2, 0)));

    List<Pixel> row2 = new ArrayList<>();
    row2.add(new Pixel(100, 100, 100, new Posn(0, 1)));
    row2.add(new Pixel(100, 100, 100, new Posn(1, 1)));
    row2.add(new Pixel(100, 100, 100, new Posn(2, 1)));

    List<Pixel> row3 = new ArrayList<>();
    row3.add(new Pixel(100, 100, 100, new Posn(0, 2)));
    row3.add(new Pixel(100, 100, 100, new Posn(1, 2)));
    row3.add(new Pixel(100, 100, 100, new Posn(2, 2)));

    resultListOfPixels.add(row1);
    resultListOfPixels.add(row2);
    resultListOfPixels.add(row3);

    Image i = ImageUtil.readFile("test/TestPPMImages/LowResCopy.jpg");

    assertEquals(new Image(resultListOfPixels, 255), i);
  }

  @Test
  public void testGetMaxValue() {
    assertEquals(255, checkerboard.getMaxValue());
  }

  @Test
  public void testGetMaxValueDifferent255() {
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

    assertEquals(10, initialImage.getMaxValue());
  }


  // correctly throws an exception if trying to read a file that does not exist
  @Test(expected = IllegalArgumentException.class)
  public void testReadInvalidFilename() {
    ImageUtil.readFile("test/TestPPMImages/ThisFileDoesNotExist.ppm");
  }

  // can still read from a file that is not a ppm file, but then eventually this test
  // will throw an IllegalArgumentException because it does not start with p3
  @Test (expected = IllegalArgumentException.class)
  public void testReadWithDifferentType() {
    new ImageUtil().readFile("test/TestPPMImages/DoesNotStartWithP3.txt");
  }

  // correctly throws an exception if trying to read a ppm that does not start with p3
  @Test (expected = IllegalArgumentException.class)
  public void testReadPPMDoesNotStartWithP3() {
    new ImageUtil().readFile("test/TestPPMImages/DoesNotStartWithP3.ppm");
  }


  @Test
  public void testOutputToPPM() {

    // make sure new file doesn't already exist
    File testFile = new File("LowResForTest.ppm");
    testFile.delete();

    new ImageUtil().exportImage("LowResForTest.ppm", ExportType.PPM,
        new Image("test/TestPPMImages/LowRes.ppm"));

    Path path = null;
    try {
      path = Files.createTempFile("LowResForTest", ".ppm");
    } catch (IOException e) {
      //
    }
    assertTrue(Files.exists(path));

    // delete new file from test
    testFile.delete();

  }

  @Test
  public void testOutputToPNG() {

    // make sure new file doesn't already exist
    File testFile = new File("LowResForTest.png");
    testFile.delete();

    File newFile = new File("LowResForTest.ppm.png");
    new ImageUtil().exportImage("LowResForTest.ppm", ExportType.PNG,
        new Image("test/TestPPMImages/LowRes.ppm"));

    Path path = null;
    try {
      path = Files.createTempFile("LowResForTest", ".png");
    } catch (IOException e) {
      //
    }
    assertTrue(Files.exists(path));

    // delete new file from test
    newFile.delete();

  }

  @Test
  public void testOutputToJPG() {

    // make sure new file doesn't already exist
    File testFile = new File("TestJPG.jpg");
    testFile.delete();

    File newFile = new File("TestJPG.jpg.jpg");
    new ImageUtil().exportImage("TestJPG.jpg", ExportType.JPG,
        new Image("test/TestPPMImages/LowRes.ppm"));

    Path path = null;
    try {
      path = Files.createTempFile("TestJPG", ".jpg");
    } catch (IOException e) {
      //
    }
    assertTrue(Files.exists(path));

    // delete new file from test
    newFile.delete();

  }





}