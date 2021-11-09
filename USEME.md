# USEME
_______________________________________

##### INSTRUCTIONS FOR RUNNING JAR FILE (ON MAC)
1. Right-click on the res folder
2. Click "new terminal at folder"
3. Type in
   - "java -jar Assignment6.jar -script path-of-script-file"
   - "java -jar Assignment6.jar -text"
   - "java -jar Assignment6.jar -interactive"
   - Program.jar will not run unless one of those 3 options above are typed  
4. Now you can use the image processor.
5. NOTE: You can only use files that are within the res folder.
   We have provided PPM, JPG, and PNG files that can be used. 
   Also, there is a multi-layered image in the res folder that 
   can be imported using: load-project (name for project). 
   Some images may be in the zipped file because we had to stay under the 125 limit.
   
_______________________________________

Our GUI view has a menu at the top with all the operations that our image processor offers.
Those same operations are listed on the right side as buttons, so the user does not have to 
go into the menu everytime to perform an operation. 

_______________________________________

***Warning***: If you try to run the same script multiple times, then it will not work the second time 
because it would be trying to make files that already has those same names. Since those files with 
its corresponding names were created the first time, you will need to delete /change the name of 
the similar-named files to run the same script a second time.

**script1.txt description**: creates two layers, loads an image to both, applies one filter to each, 
saves the image, saves all layers, applies another filter to each, changes visibility for the 
top layer, saves the image, removes the top image, removes the top layer, copies the bottom 
layer, makes invisible, quits

**script2.txt description**: loads a project with 3 layers and images, deletes all 3 of those 
layers, creates a layer, loads an image and applies a filter. gives an 
invalid command. saves the image. adds another layer, loads an image and applies two filters. 
saves the image, saves all layers of the image. make top layer invisible and saves. make 
top layer visible and remove. quit.

_____________________________________

### COMMANDS

#### For the text view: Any phrases in the parenthesis represent input that *must* come after the command.

\
If you would like to import your own project, then you have to make sure that 
you do not currently have any layers/images.
- load-project (name for project)
- ex: load-project dogFolder

\
Create a layer that has the given name:
- create-layer (name for layer)
- ex: create-layer first-layer
The given name must be one word.
- ex: hello, helloOne
- bad ex: hello One, this is layer one

\
Goes to the layer with the given name:
- current (name for layer)
- ex: current purple-layer

\
Loads the image from the given path to the current layer 
- load-file (path name for image) 
- ex: load-file dog.png

\
Applies the blur filter to the image at the current layer
- blur
- ex: blur

\
Applies the sharpen filter to image at the current layer
- sharpen 
- ex: sharpen

\
Applies the greyscale filter to the image at the current layer
- greyscale 
- ex: greyscale

\
Applies the sepia filter to the image at the current layer
- sepia 
- ex: sepia

\
Saves the image at the current layer. This saved image will have the given name and be the export
type that was specified. 
- save (path name for image)  (image export type)
- ex: save dog png  
  The path name must be one word.
- ex: hello, helloOne
- bad ex: hello One, this is layer onfile

\
Saves the entire multilayered image. The new directory will have all the images and a.txt file 
listing the ordering of the images. 
- saveAll (folder name) (image export type)
- ex: saveAll dogPics png  
  The folder name must be one word.
- ex: hello, helloOne
- bad ex: hello One, this is the folder

\
Makes the current layer invisible
- invisible 
- ex: invisible

\
Makes the current layer visible
- visible
- ex: visible

\
Removes the current layer
- remove-layer  
- ex: remove-layer

\
Removes the image from the current layer
- remove-image 
- ex: remove-image

\
Copies the current layer and then adds it as the top layer.
- copy 
- ex: copy

\
Loads a checkerboard image to the current layer. The tileSize, width in pixels, and height in
pixels must be specified for this command to work. 
- load-checkerboard (tileSize) (width) (height) 
- ex: load-checkerboard 14 6 6 

### Conditions

- The first command called in an empty model must be create-layer. The model must have 
  at least one layer before images can be loaded or manipulated.


