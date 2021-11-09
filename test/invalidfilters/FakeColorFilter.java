package invalidfilters;

import model.filter.ColorFilter;

/**
 * Represents an invalid ColorFilter, initializes a matrix of the wrong size.
 */
public class FakeColorFilter extends ColorFilter {

  /**
   * Creates a FakeColorFilter with the given matrix.
   * @param matrix a 2d array of doubles
   */
  public FakeColorFilter(double[][] matrix) {
    super(matrix);
  }


}






