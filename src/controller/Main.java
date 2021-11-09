package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import model.ILayeredImageModel;
import model.LayeredImageModel;
import view.InteractiveView;

/**
 * This main class houses the main method that is used to run this image processor. The image
 * processor allows the user to create/remove layers, load/remove images, load a checkerboard,
 * apply filters to images, make layers invisible/visible, save individual/multilayered images,
 * save an individual image, and save the multilayered image.
 */
public class Main {

  /**
   * Main method to run the image processor. The jar file only accepts 3 inputs. -script, -text, or
   * -interactive. The image processor will not be able to run with any other input.
   *
   * @param args can either be, -script path-of-script-file, -text, or -interactive
   */
  public static void main(String[] args) {

    String s = null;
    String scriptFile = null;

    if (args.length == 1) {
      s = args[0];
    } else if (args.length == 2) {
      s = args[0];
      scriptFile = args[1];
    }

    ILayeredImageModel emptyModel = new LayeredImageModel();
    IImageController controller;

    if (s.equals("-text")) {
      controller = new ImageScriptController(emptyModel, new InputStreamReader(System.in),
          System.out);

    } else if (s.equals("-script")) {

      File myObj = new File(scriptFile);

      try {
        controller = new ImageScriptController(emptyModel, new FileReader(myObj),
            System.out);

      } catch (FileNotFoundException e) {
        return; // return if the script was not valid
      }

    } else if (s.equals("-interactive")) {
      InteractiveView.setDefaultLookAndFeelDecorated(false);
      InteractiveView frame = new InteractiveView();

      controller = new ImageGUIController(emptyModel, frame);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

    } else {
      return; // return if none of the valid options were inputted
    }

    controller.runProgram();

  }

}
