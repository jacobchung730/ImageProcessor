package controller;

/**
 * The controller for the Image models.
 * The controller accepts user input to add/remove images from the model and manipulate them.
 */
public interface IImageController {

  /**
   * Starts the image processor. This method only returns when user finishes session by pressing q.
   * @throws IllegalStateException if the program fails to write to the view
   */
  void runProgram() throws IllegalStateException;

}
