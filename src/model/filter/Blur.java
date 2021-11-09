package model.filter;

import java.util.ArrayList;
import java.util.List;
import model.Pixel;

/**
 *  Represents a blur filter. Blurring softens the edges between regions on high contrast.
 */
public class Blur extends Filter {


  /**
   * Constructs a new blur filter.
   */
  public Blur() {

    super(new double[3][3]);

    this.kernel[0][0] = 0.0625;
    this.kernel[1][0] = 0.125;
    this.kernel[2][0] = 0.0625;
    this.kernel[0][1] = 0.125;
    this.kernel[1][1] = 0.25;
    this.kernel[2][1] = 0.125;
    this.kernel[0][2] = 0.0625;
    this.kernel[1][2] = 0.125;
    this.kernel[2][2] = 0.0625;

  }


  //startX , startY : representing the cell you want to find neighbors to
  // neighboring cells are within a 3x3 area
  protected List<Pixel> neighbors(int startX, int startY, int rowSize, int colSize,
      List<List<Pixel>> grid) {
    if (grid == null) {
      throw new IllegalArgumentException("grid cannot be null!");
    }

    ArrayList<Pixel> list = new ArrayList<>();

    //find all surrounding pixels by adding +/- 1 to col and row
    for (int y = startY - 1; y <= (startY + 1); y += 1) {

      for (int x = startX - 1; x <= (startX + 1); x += 1) {

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
