package model;

import java.util.Objects;

/**
 * Represents a set of x, y coordinates.
 */
public class Posn {

  private int x;
  private int y;

  /**
   * Constructs a new model.Posn.
   *
   * @param x x coordinate
   * @param y y coordinate
   */
  public Posn(int x, int y) {

    if (x < 0) {
      throw new IllegalArgumentException("x cannot be negative!");
    }

    if (y < 0) {
      throw new IllegalArgumentException("y cannot be negative!");
    }

    this.x = x;
    this.y = y;
  }

  /**
   * Return the x-coordinate of this point.
   *
   * @return the x-coordinate as an integer
   */
  public int getX() {
    return this.x;
  }

  /**
   * Return the y-coordinate of this point.
   *
   * @return the y-coordinate as an integer
   */
  public int getY() {
    return this.y;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Posn)) {
      return false;
    }

    Posn other = (Posn) o;
    return this.x == other.x
        && this.y == other.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }


}
