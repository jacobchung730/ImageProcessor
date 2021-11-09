package model.filter;

import java.util.ArrayList;
import java.util.List;
import model.IImage;
import model.Image;
import model.Pixel;
import model.Posn;
import model.RGBType;

/**
 * Represents a filter. A filter modifies a pixels color based on it's neighbors colors.
 */
public abstract class Filter implements IFilter {

  protected double[][] kernel;

  /**
   * Creates a new filter with the given kernel.
   * @param kernel a 2d array of doubles representing a kernel
   */
  public Filter(double[][] kernel) {

    if (kernel == null) {
      throw new IllegalArgumentException("kernel cannot be null!");
    }

    // enforce that kernels are squares and have odd dimensions
    if (kernel.length != kernel[0].length) {
      throw new IllegalArgumentException("Kernel must be square.");
    }
    if (kernel.length % 2 == 0) {
      throw new IllegalArgumentException("kernel must have odd dimension,");
    }

    // copy new matrix
    double[][] newMatrix = new double[kernel.length][];
    for (int i = 0; i < kernel.length; i++) {
      newMatrix[i] = new double[kernel[i].length];
      for (int j = 0; j < kernel[i].length; j++) {
        newMatrix[i][j] = kernel[i][j];
      }
    }

    this.kernel = newMatrix;
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

      // count very pixel in a row
      for (int j = 0; j < row.size(); j++) {
        Pixel p = row.get(j);
        List<Pixel> neighbors = neighbors(p.getX(), p.getY(), row.size(), grid.size(), grid);

        int newRed = applyKernel(neighbors, RGBType.RED, image.getMaxValue());
        int newGreen = applyKernel(neighbors, RGBType.GREEN, image.getMaxValue());
        int newBlue = applyKernel(neighbors, RGBType.BLUE, image.getMaxValue());

        Pixel newPixel = new Pixel(newRed, newGreen, newBlue, new Posn(p.getX(), p.getY()),
            image.getMaxValue());
        newRow.add(newPixel);
      }
      newImage.add(newRow);
    }

    return new Image(newImage, image.getMaxValue());
  }


  // apply the kernel on each pixel in the list
  private int applyKernel(List<Pixel> neighbors, RGBType color, int maxValue) {
    if (neighbors == null) {
      throw new IllegalArgumentException("matrix cannot be null!");
    }

    double newValue = 0;
    for (int i = 0; i < this.kernel.length; i++) {
      for (int j = 0; j < this.kernel[i].length; j++) {
        Pixel p = neighbors.get((this.kernel.length * i) + j);
        int colorValue;
        if (p == null) {
          colorValue = 0;
        } else {
          switch (color) {
            case RED:
              colorValue = p.getRedValue();
              break;
            case GREEN:
              colorValue = p.getGreenValue();
              break;
            case BLUE:
              colorValue = p.getBlueValue();
              break;
            default:
              throw new
                  IllegalArgumentException("Must be either a RED, GREEN, or BLUE model.RGBType!");
          }
        }

        newValue += kernel[i][j] * (double) colorValue;

      }
    }
    if (newValue > maxValue) {
      newValue = maxValue;
    } else if (newValue < 0) {
      newValue = 0;
    }
    return (int) Math.round(newValue);
  }


  // finds the neighbors of the cell at the position: (startX, startY)
  abstract List<Pixel> neighbors(int startX, int startY, int rowSize, int colSize,
      List<List<Pixel>> grid);

  //determines if a cell's (x, y) position is inside the grid
  //helper for neighbors()
  protected boolean withinGrid(int x, int y, int rowSize, int colSize) {

    if ((x < 0) || (y < 0)) {
      return false;    // x and y cannot be negative
    }
    return (x < rowSize) && (y < colSize); // within the boundaries
  }

}
