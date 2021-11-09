package model.filter;

/**
 * Represents a greyscale filter. A greyscale filter converts an image to black and white.
 */
public class Greyscale extends ColorFilter {

  /**
   * Creates a new greyscale filter by initializing the proper matrix.
   */
  public Greyscale() {
    super(new double[3][3]);

    // initialize the matrix
    for (int i = 0; i < this.matrix.length; i++) {
      this.matrix[i][0] = 0.2126;
      this.matrix[i][1] = 0.7152;
      this.matrix[i][2] = 0.0722;
    }

  }


}
