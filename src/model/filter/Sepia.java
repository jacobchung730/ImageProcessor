package model.filter;

/**
 * Represents a sepia filter. A sepia filter adds a yellow hue to an image.
 */
public class Sepia extends ColorFilter {

  /**
   * Creates a new sepia filter by initializing the proper matrix.
   */
  public Sepia() {
    super(new double[3][3]);

    this.matrix[0][0] = 0.393;
    this.matrix[1][0] = 0.349;
    this.matrix[2][0] = 0.272;
    this.matrix[0][1] = 0.769;
    this.matrix[1][1] = 0.686;
    this.matrix[2][1] = 0.534;
    this.matrix[0][2] = 0.189;
    this.matrix[1][2] = 0.168;
    this.matrix[2][2] = 0.131;

  }

}
