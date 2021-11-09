package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The InteractiveView class represents a view for the GUI. The main panel is initialized
 * with all of its sub-panels, buttons, and other components. This class has the action performed
 * method which is called whenever the user interacts with the GUI.
 */
public class InteractiveView extends JFrame implements ActionListener, IInteractiveView {

  private final List<IViewListener> listener;

  // panels 
  private final JMenuBar menuBar;
  private final JLabel imageLabel;
  private final JPanel projectControlsPanel;
  private final JPanel removeOrCopyPanel;
  private final JPanel filterPanel;
  private final JPanel visibilityPanel;
  private final JPanel currentPanel;

  private final JLabel errorMessage;
  private JLabel layerName;

  private final JMenu currentSubMenu;

  private DefaultComboBoxModel<String> comboBoxModel;

  private String currentLayer;
  

  /**
   * Constructs a new interactive view for the layered image processor.
   */
  public InteractiveView() {

    super();
    setTitle("");
    setSize(1200, 720);

    this.listener = new ArrayList<>();

    // main panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    // current layer text
    this.currentLayer = "";
    
    // menu bar
    this.menuBar = new JMenuBar();
    this.setJMenuBar(this.menuBar);
    this.currentSubMenu = new JMenu("Select Current Layer");
    setUpMenuBar();

    // image display panel
    JPanel imagePanel = new JPanel();
    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(imagePanel);
    
    this.imageLabel = new JLabel();
    this.imageLabel.setIcon(new ImageIcon());

    JScrollPane imageScrollPane = new JScrollPane(this.imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(800, 480));

    imagePanel.add(imageScrollPane);

    // buttons panel contains all other panels with buttons
    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(buttonsPanel);

    // project control buttons panel
    this.projectControlsPanel = new JPanel();
    this.projectControlsPanel.setBorder(BorderFactory.createTitledBorder("Project Controls"));
    this.projectControlsPanel
        .setLayout(new BoxLayout(this.projectControlsPanel, BoxLayout.PAGE_AXIS));
    buttonsPanel.add(this.projectControlsPanel);
    this.currentPanel = new JPanel();
    buttonsPanel.add(this.currentPanel);
    addToProjectControlsPanel();

    // individual layer controls panel
    JPanel layerControlsPanel = new JPanel();
    layerControlsPanel.setLayout(new BoxLayout(layerControlsPanel, BoxLayout.PAGE_AXIS));
    buttonsPanel.add(layerControlsPanel);

    // remove and copy layer buttons panel
    this.removeOrCopyPanel = new JPanel();
    removeOrCopyPanel.setBorder(BorderFactory.createTitledBorder("Layer Options"));
    removeOrCopyPanel.setLayout(new BoxLayout(removeOrCopyPanel, BoxLayout.PAGE_AXIS));
    layerControlsPanel.add(this.removeOrCopyPanel);
    addToRemoveOrCopyPanel();


    // visibility buttons panel
    this.visibilityPanel = new JPanel();
    visibilityPanel.setBorder(BorderFactory.createTitledBorder("Layer Visibility"));
    this.visibilityPanel.setLayout(new BoxLayout(this.visibilityPanel, BoxLayout.PAGE_AXIS));
    layerControlsPanel.add(this.visibilityPanel);
    addToVisibilityPanel();


    // filter buttons panel
    this.filterPanel = new JPanel();
    filterPanel.setBorder(BorderFactory.createTitledBorder("Filters"));
    this.filterPanel.setLayout(new BoxLayout(this.filterPanel, BoxLayout.PAGE_AXIS));
    layerControlsPanel.add(this.filterPanel);
    addToFilterPanel();

    // error panel
    JPanel errorPanel = new JPanel();
    this.errorMessage = new JLabel();
    errorPanel.add(this.errorMessage);
    buttonsPanel.add(errorPanel);

  }

  // set up the menu bar elements
  private void setUpMenuBar() {
    JMenu file = new JMenu("File");
    // load project, save, save all, load script
    JMenuItem loadProject = new JMenuItem("Load Project");
    loadProject.setActionCommand("Open project");
    loadProject.addActionListener(this);
    JMenuItem save = new JMenuItem("Save Image");
    save.setActionCommand("Save file");
    save.addActionListener(this);
    JMenuItem saveAll = new JMenuItem("Save Project");
    saveAll.setActionCommand("Save layers");
    saveAll.addActionListener(this);
    JMenuItem loadScript = new JMenuItem("Load Script");
    loadScript.setActionCommand("Load script");
    loadScript.addActionListener(this);

    // adds the menu items to the file menu
    file.add(loadProject);
    file.add(save);
    file.add(saveAll);
    file.add(loadScript);


    JMenu layer = new JMenu("Layer");
    // create layer, copy, remove layer, load file, load checkerboard, current, visible, invisible
    JMenuItem createLayer = new JMenuItem("Create New Layer");
    createLayer.setActionCommand("Create layer");
    createLayer.addActionListener(this);
    JMenuItem copyLayer = new JMenuItem("Duplicate Layer");
    copyLayer.setActionCommand("Duplicate layer");
    copyLayer.addActionListener(this);
    JMenuItem removeLayer = new JMenuItem("Remove Layer");
    removeLayer.setActionCommand("Remove layer");
    removeLayer.addActionListener(this);
    JMenuItem loadFile = new JMenuItem("Load Image");
    loadFile.setActionCommand("Open file");
    loadFile.addActionListener(this);
    JMenuItem loadCheckerboard = new JMenuItem("Load Checkerboard");
    loadCheckerboard.setActionCommand("Create checkerboard");
    loadCheckerboard.addActionListener(this);

    JMenuItem removeImage = new JMenuItem("Remove Image");
    removeImage.setActionCommand("Remove image");
    removeImage.addActionListener(this);
    JMenuItem visible = new JMenuItem("Make Layer Visible");
    visible.setActionCommand("visible");
    visible.addActionListener(this);
    JMenuItem invisible = new JMenuItem("Make Layer Invisible");
    invisible.setActionCommand("invisible");
    invisible.addActionListener(this);

    // adds the menu items to the layer menu
    layer.add(createLayer);
    layer.add(copyLayer);
    layer.add(removeLayer);
    layer.add(loadFile);
    layer.add(loadCheckerboard);
    layer.add(removeImage);
    layer.add(currentSubMenu); // has a submenu for the list of layers
    layer.add(visible);
    layer.add(invisible);

    JMenu filter = new JMenu("Filter");
    // blur, sharpen, greyscale, sepia
    JMenuItem blur = new JMenuItem("Apply Blur Filter");
    blur.setActionCommand("blur");
    blur.addActionListener(this);

    JMenuItem sharpen = new JMenuItem("Apply Sharpen Filter");
    sharpen.setActionCommand("sharpen");
    sharpen.addActionListener(this);

    JMenuItem greyscale = new JMenuItem("Apply Greyscale Filter");
    greyscale.setActionCommand("greyscale");
    greyscale.addActionListener(this);

    JMenuItem sepia = new JMenuItem("Apply Sepia Filter");
    sepia.setActionCommand("sepia");
    sepia.addActionListener(this);

    // adds the menu items to the filter menu
    filter.add(blur);
    filter.add(sharpen);
    filter.add(greyscale);
    filter.add(sepia);

    // adds the menus to the menubar
    this.menuBar.add(file);
    this.menuBar.add(layer);
    this.menuBar.add(filter);
  }

  // adds visible and invisible buttons to the visibility panel
  private void addToVisibilityPanel() {

    // visible
    JButton visibleButton = new JButton("Make layer visible");
    visibleButton.setActionCommand("visible");
    visibleButton.addActionListener(this);
    this.visibilityPanel.add(visibleButton);

    // invisible
    JButton invisibleButton = new JButton("Make layer invisible");
    invisibleButton.setActionCommand("invisible");
    invisibleButton.addActionListener(this);
    this.visibilityPanel.add(invisibleButton);

  }

  // adds buttons to the layer controls panel
  private void addToRemoveOrCopyPanel() {
    // remove layer
    JButton removeLayerButton = new JButton("Remove this layer");
    removeLayerButton.setActionCommand("Remove layer");
    removeLayerButton.addActionListener(this);
    this.removeOrCopyPanel.add(removeLayerButton);

    // copy layer
    JButton copyLayerButton = new JButton("Duplicate this layer");
    copyLayerButton.setActionCommand("Duplicate layer");
    copyLayerButton.addActionListener(this);
    this.removeOrCopyPanel.add(copyLayerButton);

    // remove image
    JButton removeImageButton = new JButton("Remove the image");
    removeImageButton.setActionCommand("Remove image");
    removeImageButton.addActionListener(this);
    this.removeOrCopyPanel.add(removeImageButton);
  }


  // sets the items in the project controls panel
  private void addToProjectControlsPanel() {

    // create layer
    JButton createLayerButton = new JButton("Create new layer");
    createLayerButton.setActionCommand("Create layer");
    createLayerButton.addActionListener(this);
    this.projectControlsPanel.add(createLayerButton);

    // create checkerboard
    JButton createCheckerboardButton = new JButton("Create checkerboard");
    createCheckerboardButton.setActionCommand("Create checkerboard");
    createCheckerboardButton.addActionListener(this);
    this.projectControlsPanel.add(createCheckerboardButton);

    // open files
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(this);
    this.projectControlsPanel.add(fileOpenButton);

    // open project
    JButton projectOpenButton = new JButton("Open a project");
    projectOpenButton.setActionCommand("Open project");
    projectOpenButton.addActionListener(this);
    this.projectControlsPanel.add(projectOpenButton);

    // save file
    JButton saveImageButton = new JButton("Save the image");
    saveImageButton.setActionCommand("Save file");
    saveImageButton.addActionListener(this);
    this.projectControlsPanel.add(saveImageButton);

    // save project layers
    JButton saveLayersButton = new JButton("Save the project");
    saveLayersButton.setActionCommand("Save layers");
    saveLayersButton.addActionListener(this);
    this.projectControlsPanel.add(saveLayersButton);

    // current
    // list of layers
    this.currentPanel.setBorder(BorderFactory.createTitledBorder("Change Layer"));
    this.currentPanel.setLayout(new BoxLayout(this.currentPanel, BoxLayout.PAGE_AXIS));

    this.layerName = new JLabel("Current Layer: " + this.currentLayer);
    JLabel selectPanel = new JLabel("Select new layer:");

    this.currentPanel.add(this.layerName);
    this.currentPanel.add(selectPanel);

    this.comboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> combobox = new JComboBox<>(comboBoxModel);

    //the event listener when an option is selected
    combobox.setActionCommand("List of layers");
    combobox.addActionListener(this);
    this.currentPanel.add(combobox);
  }


  // adds the blur, sharpen, greyscale, and sepia buttons to the filter panel
  private void addToFilterPanel() {
    // blur
    JButton blurButton = new JButton("Blur the image");
    blurButton.setActionCommand("blur");
    blurButton.addActionListener(this);
    this.filterPanel.add(blurButton);

    // sharpen
    JButton sharpenButton = new JButton("Sharpen the image");
    sharpenButton.setActionCommand("sharpen");
    sharpenButton.addActionListener(this);
    this.filterPanel.add(sharpenButton);

    // greyscale
    JButton greyscaleButton = new JButton("Greyscale the image");
    greyscaleButton.setActionCommand("greyscale");
    greyscaleButton.addActionListener(this);
    this.filterPanel.add(greyscaleButton);

    // sepia
    JButton sepiaButton = new JButton("Sepia the image");
    sepiaButton.setActionCommand("sepia");
    sepiaButton.addActionListener(this);
    this.filterPanel.add(sepiaButton);

  }

  @Override
  public void addListener(IViewListener l) {
    this.listener.add(l);
  }

  @Override
  public void displayMessage(String message) {
    this.errorMessage.setText(message);
  }

  @Override
  public void setCurrentLayerName(String s) {
    this.currentLayer = s;
    this.layerName.setText("Current Layer: " + s);
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    this.errorMessage.setText("");

    String[] options = {"ppm", "png", "jpg"};

    for (IViewListener l : this.listener) {
      switch (e.getActionCommand()) {
        case "visible":
          l.handleVisible();
          break;
        case "invisible":
          l.handleInvisible();
          break;
        case "Remove layer":
          l.handleRemoveLayer();
          break;
        case "Duplicate layer":
          l.handleCopy();
          break;
        case "Remove image":
          l.handleRemoveImage();
          break;
        case "Create layer":
          String path = JOptionPane.showInputDialog("Enter layer name");
          l.handleCreateLayer(path);
          break;
        case "Create checkerboard":
          goToHandleCheckerboard(l);
          break;
        case "Open file":
          goToOpenFile(l);
          break;
        case "Open project":
          goToOpenProject(l);
          break;
        case "Save file":
          goToSaveFile(l, options);
          break;
        case "Save layers":
          goToSaveLayers(l, options);
          break;
        case "List of layers":
          goToListOfLayers(l, e);
          break;
        case "blur":
          l.handleBlur();
          break;
        case "sharpen":
          l.handleSharpen();
          break;
        case "greyscale":
          l.handleGreyscale();
          break;
        case "sepia":
          l.handleSepia();
          break;
        case "Load script":
          goToLoadScript(l);
          break;
        default:
          throw new IllegalArgumentException("not a valid action");
      }
      revalidate();
      repaint();
    }
  }


  // asks user to input tile Size, width, and height,
  // creates a checkerboard in the model and displays it to the view
  private void goToHandleCheckerboard(IViewListener l) {
    JTextField tileSizeField = new JTextField(3);
    JTextField widthField = new JTextField(3);
    JTextField heightField = new JTextField(3);
    JPanel checkerboardPopUpPanel = new JPanel();

    checkerboardPopUpPanel.add(new JLabel("Enter a tile size: "));
    checkerboardPopUpPanel.add(tileSizeField);

    checkerboardPopUpPanel.add(new JLabel("Enter a width size: "));
    checkerboardPopUpPanel.add(widthField);

    checkerboardPopUpPanel.add(new JLabel("Enter a height size: "));
    checkerboardPopUpPanel.add(heightField);


    int result = JOptionPane.showConfirmDialog(null, checkerboardPopUpPanel,
        "Please Enter Tile Size, Width, and Height.", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.YES_OPTION) {
      String actualTileSizeInput = tileSizeField.getText();
      String actualWidthInput = widthField.getText();
      String actualHeightInput = heightField.getText();
      l.handleLoadCheckerboard(actualTileSizeInput, actualWidthInput, actualHeightInput);
    }
  }

  // asks user to choose a file
  // opens file and adds image to the model and shows image to the view
  private void goToOpenFile(IViewListener l) {
    final JFileChooser fchooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "PPM, JPG, & PNG Images", "ppm", "jpg", "png");
    fchooser.setFileFilter(filter);
    int returnValue = fchooser.showOpenDialog(InteractiveView.this);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      String filePath = f.getAbsolutePath();
      l.handleLoadFile(filePath);
    }
  }

  // asks user to choose a folder directory to open
  // opens folder and adds project to the model and shows to the view
  private void goToOpenProject(IViewListener l) {
    final JFileChooser projectChooser = new JFileChooser();
    projectChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int projectReturnValue = projectChooser.showOpenDialog(InteractiveView.this);
    if (projectReturnValue == JFileChooser.APPROVE_OPTION) {
      File f = projectChooser.getSelectedFile();
      String filePath = f.getAbsolutePath();
      l.handleLoadProject(filePath);
    }
  }

  // asks user to input name for file and specify export type
  // saves file to users system
  private void goToSaveFile(IViewListener l, String[] options) {
    String savePath = JOptionPane.showInputDialog("Enter a file name");

    int saveFileValue = JOptionPane.showOptionDialog(this, "Please choose file type", "File Type",
        JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
    if (saveFileValue == JOptionPane.YES_OPTION) {
      String layersFileType = options[saveFileValue];
      l.handleSaveAll(savePath, layersFileType);

    }
    displayMessage("file saved to " + savePath);
  }

  // asks user to enter a folder name and specify export type
  // saves project to users system
  private void goToSaveLayers(IViewListener l, String[] options) {

    String saveLayersPath = JOptionPane.showInputDialog("Enter a folder name");

    int saveLayersValue = JOptionPane.showOptionDialog(this, "Please choose file type", "File Type",
        JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
    if (saveLayersValue == JOptionPane.YES_OPTION) {
      String layersFileType = options[saveLayersValue];
      l.handleSaveAll(saveLayersPath, layersFileType);
    }
    displayMessage("project saved to " + saveLayersPath);
  }

  // goes to the layer that is clicked on
  // used in the JComboBox and also in the JMenu
  private void goToListOfLayers(IViewListener l, ActionEvent e) {
    if (e.getSource() instanceof JComboBox) {
      JComboBox<String> box = (JComboBox<String>) e.getSource();
      String s = (String) box.getSelectedItem();
      l.handleCurrent(s);
    }
    else if (e.getSource() instanceof JMenuItem) {
      JMenuItem item = (JMenuItem) e.getSource();
      String s = item.getText();
      l.handleCurrent(s);
    }
  }

  // asks user to choose a script to run
  // runs script, loads to the model, and shows to the view
  private void goToLoadScript(IViewListener l) {
    final JFileChooser scriptChooser = new JFileChooser();
    FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
        "txt files", "txt");
    scriptChooser.setFileFilter(txtFilter);
    int scriptReturnValue = scriptChooser.showOpenDialog(InteractiveView.this);
    if (scriptReturnValue == JFileChooser.APPROVE_OPTION) {
      File f = scriptChooser.getSelectedFile();
      String filePath = f.getAbsolutePath();
      l.handleScript(filePath);
    } else {
      displayMessage("cannot open that file.");
    }
  }


  @Override
  public void addLayer(String name) {
    this.comboBoxModel.insertElementAt(name, 0);
    JMenuItem newItem = new JMenuItem(name);
    newItem.setActionCommand("List of layers");
    newItem.addActionListener(this);
    this.currentSubMenu.insert(newItem, 0);
  }

  @Override
  public void removeLayer(String name) {
    if (name != null) {
      this.comboBoxModel.removeElement(name);

      // goes through the JMenuItems in the JMenu. If the JMenuItem has the name, then remove it
      for (int i = 0; i < this.currentSubMenu.getItemCount(); i++) {
        if (this.currentSubMenu.getItem(i).getText().equals(name)) {
          this.currentSubMenu.remove(i);
        }
      }

    }
    // if name is null, nothing happens
  }

  @Override
  public void setDisplayImage(BufferedImage image) {
    if (image == null) {
      this.imageLabel.setIcon(new ImageIcon());
    } else {
      this.imageLabel.setIcon(new ImageIcon(image));
    }
  }


}







