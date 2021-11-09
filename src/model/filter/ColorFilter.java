package model.filter;

import java.util.ArrayList;
import java.util.List;
import model.IImage;
import model.Image;
import model.Pixel;
import model.Posn;

/**
 * Represents a color filter. Color filters modify an pixels color based on their own
 * original color.
 */
public abstract class ColorFilter implements IFilter {

  protected double[][] matrix;

  /**
   * Creates a new color filter with the given matrix.
   * @param matrix a 2d array of doubles representing the filter matrix
   */
  public ColorFilter(double[][] matrix) {

    if (matrix == null) {
      throw new IllegalArgumentException("matrix cannot be null!");
    }

    // a matrix must have 3 rows, one for each rgb color
    if (matrix.length != 3) {
      throw new IllegalArgumentException("color transformation must have 3 rows");
    }

    // copy new matrix
    double[][] newMatrix = new double[matrix.length][];
    for (int i = 0; i < matrix.length; i++) {
      newMatrix[i] = new double[matrix[i].length];
      for (int j = 0; j < matrix[i].length; j++) {
        newMatrix[i][j] = matrix[i][j];
      }
    }

    this.matrix = newMatrix;
  }

  @Override
  public IImage applyFilter(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("image cannot be null!");
    }

    List<List<Pixel>> grid = image.getGrid();
    List<List<Pixel>> newImage = new ArrayList<>();

    for (int i = 0; i < grid.size(); i++) {
      List<Pixel> row = grid.get(i);
      List<Pixel> newRow = new ArrayList<>();
      // for each pixel in the row
      for (int j = 0; j < row.size(); j++) {
        Pixel p = row.get(j);

        int[][] oldPixelMatrix = new int[3][1];
        oldPixelMatrix[0][0] = p.getRedValue();
        oldPixelMatrix[1][0] = p.getGreenValue();
        oldPixelMatrix[2][0] = p.getBlueValue();

        int[][] rgbColors = multiplyMatrices(oldPixelMatrix, 1, image.getMaxValue());

        int r = rgbColors[0][0];
        int g = rgbColors[1][0];
        int b = rgbColors[2][0];

        Pixel newP = new Pixel(r, g, b, new Posn(p.getX(), p.getY()), image.getMaxValue());
        newRow.add(newP);
      }
      newImage.add(newRow);
    }
    return new Image(newImage, image.getMaxValue());
  }


  // multiplies the given matrix to this.matrix
  private int[][] multiplyMatrices(int[][] secondMatrix, int c2, int maxValue) {
    int r1 = this.matrix.length;
    int[][] product = new int[r1][c2];
    for (int i = 0; i < r1; i++) {
      int c1 = this.matrix[i].length;
      for (int j = 0; j < c2; j++) {
        double newValue = 0;
        for (int k = 0; k < c1; k++) {
          newValue += (this.matrix[i][k] * (double) secondMatrix[k][j]);
        }
        if (newValue > maxValue) {
          product[i][j] = maxValue;
        } else if (newValue < 0) {
          product[i][j] = 0;
        }
        else {
          product[i][j] = (int) Math.round(newValue);
        }
      }
    }
    return product;
  }




}
