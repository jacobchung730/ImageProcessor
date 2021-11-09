package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory class that creates a checkerboard image.
 */
public class CheckerboardImage {

  private final  int tileSize;
  // width and height by tile
  private final int width;
  private final int height;


  /**
   * Creates a new checkerboard.
   * @param tileSize pixels per tile
   * @param width width of board
   * @param height height of board
   */
  public CheckerboardImage(int tileSize, int width, int height)
      throws IllegalArgumentException {
    if (tileSize < 0 || width < 0 || height < 0) {
      throw new IllegalArgumentException("Cannot have negative value.");
    }
    this.tileSize = tileSize;
    this.width = width;
    this.height = height;
  }

  /**
   * Creates a checkerboard image programmatically.
   * @return the checkerboard image
   */
  public IImage createCheckerboard() {
    List<List<Pixel>> grid = new ArrayList<>();
    for (int i = 0; i < this.height; i++) {
      List<Pixel> row;
      // alternating between making rows starting with black or white tiles
      if (i % 2 == 0) {
        // start white
        for (int j = 0; j < this.tileSize; j++) {
          row = createRow(255, 0, (i * this.tileSize) + j);
          grid.add(row);
        }
      }
      else {
        // start black
        for (int m = 0; m < this.tileSize; m++) {
          row = createRow(0, 255, (i * this.tileSize) + m);
          grid.add(row);
        }
      }
    }
    return new Image(grid, 255);
  }

  // creates a row of pixels
  // used in the createCheckerboard method
  private List<Pixel> createRow(int colorValue, int altColorValue, int y) {
    List<Pixel> row = new ArrayList<>();
    for (int j = 0; j < this.width; j++) { // width = 3
      if (j % 2 == 0) {
        for (int k = 0; k < this.tileSize; k++) {
          Pixel p = new Pixel(colorValue, colorValue, colorValue,
              new Posn((j * tileSize) + k, y));
          row.add(p);
        }
      } else {
        for (int k = 0; k < this.tileSize; k++) {
          Pixel p = new Pixel(altColorValue, altColorValue, altColorValue,
              new Posn((j * tileSize) + k, y));
          row.add(p);
        }
      }
    }
    return row;
  }




}