
Image Manipulation and Processing
===============================================  

Class Design
------------

#### Image representation:

The IImage interface provides methods that all Images should be able to provide, regardless of 
the type of Image. An IImage is represented by a 2D list of pixels.

The Image class implements the IImage interface. An Image is represented by a 2D list of pixels.
Each pixel in this image will have a value that the pixel's RBG will be padded at. 

The Pixel class represents a single pixel with a RED, GREEN, and BLUE value. These values
will range from 0-255 if it has three 8-bit channels. An Image consists of a 2D array of pixels
where each pixel will have its own position.

The RGBType enum class represents the three possible channels that a Pixel has: RED, GREEN,
and BLUE.

The ExportType enum class represents the three possible export types that we are supporting at the 
moment: PPM, JPG, and PNG.

The Posn class represents a set of (x, y) coordinates, mainly used when giving a pixel a
position in a 2D array of an Image.

The ImageUtil class is contains utility methods that can parse a PPM files to create an Image and
also create a PPM file using a given image.


#### The Model:

The ISimpleImageModel interface provides methods that all single processing models should be able 
to support.

The SimpleImageModel class represents the model for the simple image processor. A filter can be 
applied to the list of images of this model. 

The ILayeredImageModel interface provides methods that all layered image processing models
should be able to support. 

The LayeredImageModel implements the ILayeredImageModel. It represents a layered image with two 
maps: one maps layer names with their Image contents, and the other maps the layer names with
their visibility (can be toggled visible or invisible). The LayeredImageModel performs
all the functionality of the SimpleImageModel, but the main difference is that images are 
separated by layer rather than being together in one long list.


#### Filter representation:

The IFilter interface provides a method that can apply a filter to the given image. 

The Filter class is an abstract class that holds operations all non-color filters have in common. 
All these filters manipulate the image by adjusting its pixel's colors in relation to their
neighbors colors.

The ColorFilter class an abstract class that holds operations that all color filters have in
common. Color filters manipulate an image by adjusting its pixels colors in relation to their own
color.

The Blur and Sharpen classes are both Filters that have their own unique kernel that will adjust 
the given image accordingly. 

The Greyscale and Sepia classes are both ColorFilters that have their own unique matrix that
will adjust the image accordingly.


#### Creating images programmatically: 

The CheckerboardImage factory class provides a way to programmatically construct a checkerboard 
image using the given tileSize (in pixels), and width and height (in tiles).


#### The Controller

The IImageController interface has the runProgram method that initializes the model.

The ImageScriptController implements IImageController. This controller uses the LayeredImageModel 
since the LayeredImageModel supports functionality from the SimpleImageModel plus more. The 
controller supports a set of commands that the client can use to manipulate with the model. 
Input can be passed in interactively or through a script. This controller uses the IImageTextView 
to communicate feedback about the script commands execution.

The ImageGUIController implements IImageController. This controller uses the LayeredImageModel as
the model and the IInteractiveView as the view. It supports scripting via the ImageScriptController
and takes input from a GUI managed by view.

#### Textual View

The IImageTextView interface has the renderMessage method that allows strings to be outputted 
to the desired Appendable.

The ImageTextView class implements IImageTextView. An appendable must be passed into 
the constructor to create an ImageTextView. The text view returns messages to inform the
user on whether their commands successfully executed.

#### The GUI

The IInteractiveView interface holds all the operations that a GUI for a layered image model needs
to support, which includes access to ways the user might n eed to manipulate the model.

The InteractiveView class implements IInteractiveView. It creates a new GUI that has all 
functionality stored in a menu bar, and in easy access buttons on the side of the image workspace, 
a large scrollable area where the visible image in the model can be viewed.

The IViewListener interface is implemented by the ImageGUIController. This interface houses all
the operations that the controller can pass on to the model. 



#### Design Decisions and Changes from Assignment 5

1. ImageUtil class
   
   This class handles all I/O so we moved it from the model to the controller as the model should 
   not be handling I/O. All methods were changed to static methods as they only perform simple
   utility functions.     

2. Although our controller is now supporting the ILayeredImageModel, our old Model, 
   ISimpleImageModel, had a major flaw. When passed a Map<String, IImage>, we returned a 
   direct reference to a mutable field in a public method. Now, we have made it so that
   the constructor will return a deep copy. 

3. We decided that our controller would support the ILayeredImageModel and not the ISimpleImageModel
   since the layered model performed all functionality of the simple model, plus more. This way, 
   clients can use the model as a layered model or as a simple model yt ignoring commands that
   handle layer visibility and manipulation.
   
#### Assignment 6 Changes
We were way over the 125 file limit. Therefore, we decided to combine all of our command tests 
into a class called "CommandTest"

