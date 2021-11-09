package model;

/**
 * Represents the possible export types for an image. An image can either be exported as
 * ppm, jpg, or png.
 */
public enum ExportType {
  PPM("ppm"), JPG("jpg"), PNG("png");

  private final String s;

  ExportType(String s) {
    this.s = s;
  }


  /**
   * Returns the string corresponding to its enum.
   * @return the string from the enum
   */
  public String exportString() {
    return this.s;
  }

  /**
   * Returns the enum corresponding to the given string.
   * @param s the string
   * @return the ExportType
   */
  public static ExportType getEnum(String s) {
    switch (s) {
      case "png":
        return ExportType.PNG;
      case "jpg":
        return ExportType.JPG;
      case "ppm":
        return ExportType.PPM;
      default:
        throw new IllegalArgumentException("not a valid string for ExportType");
    }
  }

}

