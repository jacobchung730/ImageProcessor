import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import fakeappendable.FakeAppendable;
import java.io.IOException;
import org.junit.Test;
import view.IImageTextView;
import view.ImageTextView;

/**
 * Tests for the ImageTextView class.
 */
public class ImageTextViewTest {


  @Test (expected = IllegalArgumentException.class)
  public void testImageTextViewAppendableNull() {
    new ImageTextView(null);
  }

  // throws and exception when trying to call renderBoard on a bad appendable
  @Test(expected = IOException.class)
  public void testIOExceptionInRenderBoard() throws IOException {

    new ImageTextView(new FakeAppendable()).renderMessage("hi");
  }

  @Test
  public void testRenderMessage() {

    StringBuilder sb = new StringBuilder();

    IImageTextView view = new ImageTextView(sb);

    try {
      view.renderMessage("hi");
    } catch (IOException e) {
      assertNotEquals("hi", sb.toString());
    }
    assertEquals("hi", sb.toString());


  }




}
