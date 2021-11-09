package controller;

import controller.commands.BlurCommand;
import controller.commands.Copy;
import controller.commands.CreateLayer;
import controller.commands.Current;
import controller.commands.GreyscaleCommand;
import controller.commands.Invisible;
import controller.commands.LoadCheckerboard;
import controller.commands.LoadFile;
import controller.commands.LoadProject;
import controller.commands.RemoveImage;
import controller.commands.RemoveLayer;
import controller.commands.Save;
import controller.commands.SaveAll;
import controller.commands.SepiaCommand;
import controller.commands.SharpenCommand;
import controller.commands.Visible;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import model.ILayeredImageModel;
import view.IImageTextView;
import view.ImageTextView;


/**
 * The controller for the ILayeredImageModel. This controller class has a constructor that must
 * take in the model, a readable, and an appendable. The run program method allows the image
 * processor to be run.
 */
public class ImageScriptController implements IImageController {

  private final ILayeredImageModel model;
  private final Scanner sc;
  private final IImageTextView view;
  private final Map<String, Function<Scanner, IImageCommand>> commands;


  /**
   * Constructs a {@code ImageController} object using the given model, readable, and appendable.
   * @param model a ILayeredImageModel that will be used in the view
   * @param rd a readable that the controller will read from
   * @param ap an appendable used to construct the view
   * @throws IllegalArgumentException if model, rd, or ap is null
   */
  public ImageScriptController(ILayeredImageModel model, Readable rd, Appendable ap) throws
      IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null.");
    }

    if (rd == null) {
      throw new IllegalArgumentException("rd cannot be null.");
    }

    if (ap == null) {
      throw new IllegalArgumentException("ap cannot be null.");
    }

    this.model = model;
    this.sc = new Scanner(rd);
    this.view = new ImageTextView(ap);
    this.commands = new HashMap<>();
    setCommands();
  }



  @Override
  public void runProgram() throws IllegalStateException {

    try {
      this.view.renderMessage("you may start typing commands\n");
    } catch (IOException ex) {
      throw new IllegalStateException("could not render message");
    }


    while (this.sc.hasNext()) {

      IImageCommand c;
      String input = this.sc.next();

      if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
        return;
      }

      Function<Scanner, IImageCommand> cmd = null;
      try {
        cmd = this.commands.getOrDefault(input, null);
      }
      catch (IllegalArgumentException e) {
        try {
          this.view.renderMessage("invalid command + " + e.getMessage() + "\n");
        } catch (IOException ex) {
          throw new IllegalStateException("could not render message");
        }
      }

      if (cmd == null) {
        try {
          this.view.renderMessage("invalid command\n");
        } catch (IOException e) {
          throw new IllegalStateException("could not render message");
        }
      } else {

        c = cmd.apply(sc);

        try {
          c.applyCommand(this.model);
          try {
            this.view.renderMessage("command: " + input + " executed.\n");
          } catch (IOException e) {
            throw new IllegalStateException("could not render message");
          }
        }
        catch (IllegalArgumentException e) {
          try {
            this.view.renderMessage("command failed. reason: " + e.getMessage() + "\n");
          } catch (IOException ioException) {
            throw new IllegalStateException("could not render message");
          }
        }

      }
    }

  }

  // set the commands available in scripting
  /**
   * The mapping for the commands that the image processor supports. This is run when
   * the ImageController constructor is called.
   */
  private void setCommands() {
    this.commands.put("create-layer",  (Scanner sc) -> new CreateLayer(sc.next()));
    this.commands.put("current", (Scanner sc) -> new Current(sc.next()));
    this.commands.put("load-file",  (Scanner sc) -> new LoadFile(sc.next()));
    this.commands.put("blur", (Scanner sc) -> new BlurCommand());
    this.commands.put("sharpen", (Scanner sc) -> new SharpenCommand());
    this.commands.put("greyscale", (Scanner sc) -> new GreyscaleCommand());
    this.commands.put("sepia", (Scanner sc) -> new SepiaCommand());
    this.commands.put("save", (Scanner sc) -> new Save(sc.next(), sc.next()));
    this.commands.put("saveAll", (Scanner sc) -> new SaveAll(sc.next(), sc.next()));
    this.commands.put("invisible", (Scanner sc) -> new Invisible());
    this.commands.put("visible", (Scanner sc) -> new Visible());
    this.commands.put("remove-layer", (Scanner sc) -> new RemoveLayer());
    this.commands.put("remove-image", (Scanner sc) -> new RemoveImage());
    this.commands.put("copy", (Scanner sc) -> new Copy());
    this.commands.put("load-checkerboard",
        (Scanner sc) -> new LoadCheckerboard(sc.next(), sc.next(), sc.next()));
    this.commands.put("load-project", (Scanner sc) -> new LoadProject(sc.next()));
  }




}
