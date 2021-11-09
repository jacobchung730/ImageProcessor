import static org.junit.Assert.assertEquals;

import model.ExportType;
import org.junit.Test;

/**
 * Tests for the ExportType enum.
 */
public class ExportTypeTest {


  @Test
  public void getStringPPM() {
    assertEquals("ppm", ExportType.PPM.exportString());
  }

  @Test
  public void getStringJPG() {
    assertEquals("jpg", ExportType.JPG.exportString());
  }

  @Test
  public void getStringPNG() {
    assertEquals("png", ExportType.PNG.exportString());
  }

  @Test
  public void getEnumPPM() {
    assertEquals(ExportType.PPM, ExportType.getEnum("ppm"));
  }

  @Test
  public void getEnumJPG() {
    assertEquals(ExportType.JPG, ExportType.getEnum("jpg"));
  }

  @Test
  public void getEnumPNG() {
    assertEquals(ExportType.PNG, ExportType.getEnum("png"));
  }

}
