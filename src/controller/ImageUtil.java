package controller;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import model.ExportType;
import model.IImage;
import model.ILayeredImageModel;
import model.Image;
import model.Pixel;
import model.Posn;

/**
 * This class contains utility methods to read and write images. Supports the Image implementation
 * of IImage.
 * It can read a PPM/PNG/JPG file and convert it to an Image. It can also read a layered image from
 * a folder containing each layer as an image file.
 * It can export IImages to a ppm/png/jpg file, and export a layered image as a group of image
 * files.
 */
public class ImageUtil {

  /**
   * Reads the file from the given filename and returns a enw {@code IImage} containing the
   * image data.
   * @param filename the image source file name
   * @return the image as an IImage
   * @throws IllegalArgumentException if the filename is invalid or null
   */
  public static Image readFile(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("file name is null.");
    }
    int index = filename.lastIndexOf(".");
    if (index != -1) {
      String fileType = filename.substring(index);
      switch (fileType) {
        case ".ppm":
          return readPPMToImage(filename);
        case ".jpg": // fall through to readJPGAndPNG()
        case ".png":
          return readJPGAndPNG(filename);
        default:
          throw new IllegalArgumentException("given filename is not a valid fileType.");
      }
    }
    else {
      throw new IllegalArgumentException("Invalid file name.");
    }
  }


  /**
   * Read an image file in the PPM format and print the colors as a list of list of pixels.
   * @param filename the path of the file.
   * @return all the pixels from this PPM file.
   * @throws IllegalArgumentException if the filename is null
   */
  private static Image readPPMToImage(String filename) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("invalid filename!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("file does not start with p3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    List<List<Pixel>> grid = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      List<Pixel> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        Pixel p = new Pixel(r, g, b, new Posn(j, i));
        row.add(p);

      }
      grid.add(row);
    }
    return new Image(grid, maxValue);
  }

  /**
   * Read a jpg or png image.
   * @param filename file path
   * @return the image pixel grid
   * @throws IllegalArgumentException if the filename is invalid
   */
  public static Image readJPGAndPNG(String filename) throws IllegalArgumentException {
    BufferedImage image;
    int maxValue = 255;

    image = readPathToBufferedImage(filename);

    Raster pixelArray = image.getRaster();

    List<List<Pixel>> grid = new ArrayList<>();
    for (int i = 0; i < image.getHeight(); i++) {
      List<Pixel> row = new ArrayList<>();

      for (int j = 0; j < image.getWidth(); j++) {
        int[] rgb = new int[4];
        rgb = pixelArray.getPixel(j, i, rgb);

        int r = rgb[0];
        int g = rgb[1];
        int b = rgb[2];

        Pixel p = new Pixel(r, g, b, new Posn(j, i));
        row.add(p);

      }
      grid.add(row);
    }
    return new Image(grid, maxValue);
  }

  /**
   * Turns a JPG or PNG file into a BufferedImage.
   * @param filename the file path
   * @return JPG or PNG as a BufferedImage
   * @throws IllegalArgumentException if filename is null
   */
  public static BufferedImage readFileToBufferedImage(String filename)
      throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("filename cannot be null");
    }

    return readPathToBufferedImage(filename);
  }

  // reads the given filename and exports it into a BufferedImage
  private static BufferedImage readPathToBufferedImage(String filename) throws
      IllegalArgumentException {

    String fileType = filename.substring(filename.lastIndexOf("."));
    switch (fileType) {
      case ".ppm":
        return iImageToBufferedImage(readPPMToImage(filename));
      case ".jpg": // fall through to readJPGAndPNG()
      case ".png":
        try {
          return ImageIO.read(new File(filename));
        } catch (IOException e) {
          throw new IllegalArgumentException("not a valid jpg or png.");
        }
      default:
        throw new IllegalArgumentException("given filename is not a valid fileType.");
    }
  }

  /**
   * Reads a layer image file (a folder containing each layer image) and converts it to a
   * Map containing the name of each file and the corresponding IImage.
   * @param name the source folder name
   * @return the Map of each layer name and it's image
   * @throws IllegalArgumentException if the name is null or invalid
   */
  public static Map<String, IImage> readLayeredImage(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("folder name is null.");
    }
    Scanner sc = null;

    File folder = new File(name);
    File[] listOfFiles = folder.listFiles();

    // get the txt file containing layer order
    for (File file : listOfFiles) {
      if (file.isFile()) {
        if (file.getName().substring(file.getName().lastIndexOf(".")).equals(".txt")) {
          try {
            sc = new Scanner(new FileInputStream(file));
          } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("invalid filename!");
          }
        }
      }
    }

    // if txt file is never found, scanner is never created
    if (sc == null) {
      throw new IllegalArgumentException("txt file not found");
    }

    Map<String, IImage> layersOfImages = new HashMap<>();

    while (sc.hasNext()) {
      String s = sc.next();

      if (Files.exists(Path.of(name + "/" + s))) {
        IImage image = new Image((readFile(name + "/" + s)));
        layersOfImages.put(s.substring(0, s.lastIndexOf(".")), image);
      }
    }

    return layersOfImages;
  }

  /**
   * Exports an IImage to a file of the given file type.
   * @param name the name of the file to be created
   * @param exportType the export type of the file
   * @param image the image source
   * @throws IllegalArgumentException if the export type is invalid
   */
  public static void exportImage(String name, ExportType exportType, IImage image)
      throws IllegalArgumentException {
    switch (exportType) {
      case PPM:
        outputPPMToFile(image, name);
        break;
      case PNG:
        exportToJPGOrPNG(image, ExportType.PNG, new File(name + ".png"));
        break;
      case JPG:
        exportToJPGOrPNG(image, ExportType.JPG, new File(name + ".jpg"));
        break;
      default:
        throw new IllegalArgumentException("invalid export type");
    }
  }

  /**
   * Exports a MultiLayeredImage that has all the images with a corresponding .txt file that
   * has the ordering of layers in it.
   * @param model the LayeredImage model that will be exported
   * @param folderName the name given to the new folder
   * @param exportType the type the images will be exported to
   * @throws IllegalArgumentException if the folder name already exists
   */
  public static void exportLayeredImage(ILayeredImageModel model, String folderName,
      ExportType exportType) throws IllegalArgumentException {
    Map<String, IImage> images = model.getLayerContents();

    File returnFile = new File(folderName);
    if (!returnFile.mkdir()) {
      throw new IllegalArgumentException("folder name already exists.");
    }
    File txt = new File(folderName + "/" + folderName + ".txt");
    StringBuilder stringBuilder = new StringBuilder();

    for (Map.Entry<String, IImage> i : images.entrySet()) {
      if (i.getValue() != null) {
        String imageName = i.getKey();
        IImage currentLayer = i.getValue();

        exportImage(folderName + "/" + imageName, exportType, currentLayer);

        stringBuilder.append(imageName + "." + exportType.exportString() + "\n");

      }
    }

    try {
      FileWriter myWriter = new FileWriter(txt);
      myWriter.write(stringBuilder.toString());
      myWriter.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("cannot write to this txt file!");
    }

  }

  /**
   * Returns a PPM file of the values from the given image. Names the file using the given name.
   *
   * @param image image that will have its values put into the file
   * @param name  the desired name for the new file
   * @return a new file with P3, width and height, max value, and RGB values
   * @throws IllegalArgumentException if image or name are null, or if given name already exists
   * @throws IllegalStateException if writing the image to the file fails
   */
  private static File outputPPMToFile(IImage image, String name)
      throws IllegalArgumentException, IllegalStateException {
    List<List<Pixel>> grid = image.getGrid();
    File newFile = new File(name);

    try {
      if (!newFile.createNewFile()) {
        throw new IllegalArgumentException("file already exists!");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("cannot create a new PPM file!");
    }

    StringBuilder stringBuilder = new StringBuilder();

    // basic info
    stringBuilder.append("P3\n");
    stringBuilder.append(grid.get(0).size()).append(" ");
    stringBuilder.append(grid.size()).append("\n");
    stringBuilder.append(image.getMaxValue()).append("\n");

    // pixel info
    for (int i = 0; i < grid.size(); i++) {
      List<Pixel> row = grid.get(i);

      // count every pixel in a row
      for (int j = 0; j < row.size(); j++) {
        Pixel p = row.get(j);
        stringBuilder.append(p.getRedValue()).append("\n");
        stringBuilder.append(p.getGreenValue()).append("\n");
        stringBuilder.append(p.getBlueValue()).append("\n");
      }
    }

    try {
      FileWriter myWriter = new FileWriter(newFile);
      myWriter.write(stringBuilder.toString());
      myWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("cannot write to this PPM file!");
    }
    return newFile;
  }


  // converts an IImage to a jpg or png file
  private static void exportToJPGOrPNG(IImage image, ExportType exportType, File returnFile)
      throws IllegalArgumentException {

    BufferedImage layerImage = ImageUtil.iImageToBufferedImage(image);


    try {
      ImageIO.write(layerImage, exportType.exportString(), returnFile);
    } catch (IOException e) {
      throw new IllegalArgumentException("unable to add image to this folder.");
    }
  }

  // converts an IImage to a BufferedImage
  private static BufferedImage iImageToBufferedImage(IImage image) {
    BufferedImage layerImage = new BufferedImage(image.getWidth(),
        image.getHeight(), BufferedImage.TYPE_INT_RGB);

    List<List<Pixel>> grid = image.getGrid();

    for (int j = 0; j < image.getHeight(); j++) {
      for (int k = 0; k < image.getWidth(); k++) {

        Pixel oldPixelRGB = grid.get(j).get(k);

        int r = oldPixelRGB.getRedValue();
        int g = oldPixelRGB.getGreenValue();
        int b = oldPixelRGB.getBlueValue();

        int rgb = (r << 16) | (g << 8) | b;

        layerImage.setRGB(k, j, rgb);
      }
    }

    return layerImage;
  }

  /**
   * Converts an IImage to a Buffered image.
   * @param image an IImage
   * @return a BufferedImage equivalent to the given IImage
   * @throws IllegalArgumentException if the image is null
   */
  public static BufferedImage getIImageToBufferedImage(IImage image) {
    if (image == null) {
      return null;
    }
    return iImageToBufferedImage(image);
  }



}

