package model;

import static controller.ImageUtil.readFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an Image class, where each image has a list of list of pixels. This class has two
 * constructors where one takes in a list of list of pixels and a max value, while the other takes
 * in a filname as a string.
 */
public class Image implements IImage {

  private final List<List<Pixel>> pixelGrid;
  private final int maxValue;
  private final int width;
  private final int height;

  /**
   * Creates an {@code Image} object using the given list of list of pixels.
   * @param grid a list of list of pixels.
   * @throws IllegalArgumentException if the grid is null, if the grid contains null pixels,
   *                                  or if max value is invalid
   */
  public Image(List<List<Pixel>> grid, int maxValue) throws IllegalArgumentException {

    if (grid == null) {
      throw new IllegalArgumentException("grid cannot be null!");
    }
    if (maxValue < 0) {
      throw new IllegalArgumentException("max value cannot be less than 0");
    }
    for (List<Pixel> l : grid) {
      for (Pixel p : l) {
        if (p == null) {
          throw new IllegalArgumentException("grid contains null pixel!");
        }
      }
    }

    this.pixelGrid = copyGrid(grid);
    this.maxValue = maxValue;
    this.width = grid.get(0).size();
    this.height = grid.size();

  }

  /**
   * Creates a new image that is the same as the given image.
   * @param i the given image
   */
  public Image(Image i) {
    this.pixelGrid = i.getGrid();
    this.maxValue = i.getMaxValue();
    this.width = i.getWidth();
    this.height = i.getHeight();
  }

  /**
   * creates an Image as a list of list of pixels using the given PPM file.
   * @param filename the given PPM file
   * @throws IllegalArgumentException if the filename is invalid
   */
  public Image(String filename) throws IllegalArgumentException {
    this(readFile(filename));
  }


  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public IImage duplicateImage() {
    return new Image(copyGrid(this.pixelGrid), this.maxValue);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Image)) {
      return false;
    }

    Image other = (Image)o;

    if (this.pixelGrid.size() != other.pixelGrid.size()) {
      return false;
    }

    for (int i = 0; i < this.pixelGrid.size(); i++) {
      List<Pixel> l = this.pixelGrid.get(i);
      List<Pixel> l2 = other.pixelGrid.get(i);
      for (int j = 0; j < l.size(); j++) {
        if (!l.get(j).equals(l2.get(j))) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pixelGrid.hashCode());
  }

  @Override
  public List<List<Pixel>> getGrid() {
    return copyGrid(this.pixelGrid);
  }

  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  // makes a copy of the given grid
  private List<List<Pixel>> copyGrid(List<List<Pixel>> grid) {
    List<List<Pixel>> gridCopy = new ArrayList<>();
    for (int i = 0; i < grid.size(); i++) {

      List<Pixel> rowCopy = new ArrayList<>();
      for (int j = 0; j < grid.get(0).size(); j++) {

        Pixel oldPixel = grid.get(i).get(j);

        Pixel newPixel = new Pixel(oldPixel.getRedValue(), oldPixel.getGreenValue(),
            oldPixel.getBlueValue(), new Posn(oldPixel.getX(), oldPixel.getY()));

        rowCopy.add(newPixel);
      }
      gridCopy.add(rowCopy);
    }
    return gridCopy;
  }


}





