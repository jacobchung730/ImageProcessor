package model;

import java.util.Objects;

/**
 * represents a Pixel object which has a red, green, and blue value, as well as a position.
 */
public class Pixel {

  private final int red;
  private final int green;
  private final int blue;
  private final Posn position;


  /**
   * Constructs a new pixel.
   * @param red red color value
   * @param green green color value
   * @param blue blue color value
   * @param position pixel coordinates
   * @param maxValue the max color value for the pixel
   * @throws IllegalArgumentException position cannot be null
   */
  public Pixel(int red, int green, int blue, Posn position, int maxValue)
      throws IllegalArgumentException {
    if (position == null) {
      throw new IllegalArgumentException("Cannot have null position.");
    }

    if (red > maxValue || red < 0) {
      throw new IllegalArgumentException("not a valid int for red!");
    }

    if (green > maxValue || green < 0) {
      throw new IllegalArgumentException("not a valid int for green!");
    }

    if (blue > maxValue || blue < 0) {
      throw new IllegalArgumentException("not a valid int for blue!");
    }

    this.red = red;
    this.green = green;
    this.blue = blue;
    this.position = position;
  }

  /**
   * Convenience constructor to construct a new {@code Pixel} which sets the maxValue value to 255.
   *
   * @param red      red color value
   * @param green    green color value
   * @param blue     blue color value
   * @param position the position of this pixel
   * @throws IllegalArgumentException if any color value is invalid or the position is null
   */
  public Pixel(int red, int green, int blue, Posn position) throws IllegalArgumentException {
    this(red, green, blue, position, 255);

  }

  /**
   * Return the x-coordinate of this point.
   *
   * @return the x-coordinate as an integer
   */
  public int getX() {
    return this.position.getX();
  }

  /**
   * Return the y-coordinate of this point.
   *
   * @return the y-coordinate as an integer
   */
  public int getY() {
    return this.position.getY();
  }

  /**
   * Returns the red value for this pixel.
   *
   * @return red value
   */
  public int getRedValue() {
    return this.red;
  }

  /**
   * Returns the green value for this pixel.
   *
   * @return green value
   */
  public int getGreenValue() {
    return this.green;
  }

  /**
   * Returns the blue value for this pixel.
   *
   * @return blue value
   */
  public int getBlueValue() {
    return this.blue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Pixel)) {
      return false;
    }

    Pixel other = (Pixel) o;
    return this.red == other.red
        && this.green == other.green
        && this.blue == other.blue
        && this.position.equals(other.position);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue, this.position.hashCode());
  }


}
