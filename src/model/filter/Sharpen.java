package model.filter;

import java.util.ArrayList;
import java.util.List;
import model.Pixel;

/**
 * Represents a sharpen filter. Sharpening accentuates edges, thereby giving the
 * image a 'sharper" look.
 */
public class Sharpen extends Filter {

  /**
   * Creates a new Sharpen object. Initializes the 5x5 sharpen kernel.
   */
  public Sharpen() {
    super(new double[5][5]);

    for (int i = 0; i < this.kernel.length; i++) {
      for (int j = 0; j < this.kernel[i].length; j++) {
        // borders
        if (i == 0 || j == 0 || i == this.kernel.length - 1 || j == this.kernel[i].length - 1) {
          this.kernel[i][j] = -0.125;
        }
        // center
        else if (i == 2 && j == 2) {
          this.kernel[i][j] = 1;
        }
        // everywhere else in the middle
        else {
          this.kernel[i][j] = 0.25;
        }
      }
    }
  }

  //startX , startY : representing the cell you want to find neighbors to
  // this neighbors method differs in that neighbors all cells within the 5x5 area
  protected List<Pixel> neighbors(int startX, int startY, int rowSize, int colSize,
      List<List<Pixel>> grid) {
    if (grid == null) {
      throw new IllegalArgumentException("grid cannot be null!");
    }
    ArrayList<Pixel> list = new ArrayList<>();

    //find all surrounding pixels by adding +/- 1 to col and row
    for (int y = startY - 2; y <= (startY + 2); y += 1) {

      for (int x = startX - 2; x <= (startX + 2); x += 1) {

        //make sure it is within grid
        if (super.withinGrid(x, y, rowSize, colSize)) {
          Pixel p = grid.get(y).get(x);
          list.add(p);
        }
        else {
          list.add(null);
        }
      }
    }
    return list;
  }
}
