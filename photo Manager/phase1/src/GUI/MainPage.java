package GUI;

import Controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainPage implements Initializable {

    /**
     * Choose button in Javafx.
     */
    @FXML
    public Button chooseBtn;
    /**
     * Remove button in Javafx.
     */
    @FXML
    public Button removeBtn;
    /**
     * Move button in Javafx.
     */
    @FXML
    public Button movePhoto;
    /**
     * Remove button in Javafx.
     */
    @FXML
    public Button removeTagFromPhoto;
    /**
     * Photo tab in Javafx.
     */
    @FXML
    public TableView<Photo> photoTab;
    /**
     * Name column in Javafx.
     */
    @FXML
    public TableColumn<Photo, String> fileNameCol;
    /**
     * Location column in Javafx.
     */
    @FXML
    public TableColumn<Photo, String> locationCol;
    /**
     * Image view in load Javafx.
     */
    @FXML
    public ImageView imageView;
    /**
     * Image view in view Javafx.
     */
    @FXML
    public ImageView displayPhoto;
    /**
     * Load button in Javafx.
     */
    @FXML
    public Button loadBtn;
    /**
     * View history button in Javafx.
     */
    @FXML
    public Button viewTagHistory;
    /**
     * Remove tag button in Javafx.
     */
    @FXML
    public Button removeEntireTag;
    /**
     * Directory label in Javafx.
     */
    @FXML
    public Label directLab;
    /**
     * View photo tab in Javafx.
     */
    @FXML
    public Tab viewPhoto;
    /**
     * Add tag button in Javafx.
     */
    @FXML
    public Button addTag;
    /**
     * Tag list view in Javafx.
     */
    @FXML
    public ListView<String> avaTagListView;
    /**
     * Photo tableview in Javafx.
     */
    @FXML
    public TableView<Photo> photoTagTable;
    /**
     * Photo name column in Javafx.
     */
    @FXML
    public TableColumn<Photo, String> photoNameCol;
    /**
     * Tags column in Javafx.
     */
    @FXML
    public TableColumn<Photo, String> tagsInTableCol;
    /**
     * Tag choice box in Javafx.
     */
    @FXML
    public ComboBox<String> tagChoiceBox;
    /**
     * Photo plane in Javafx.
     */
    @FXML
    public TabPane photoPlane;
    /**
     * Load tab in Javafx.
     */
    @FXML
    public Tab loadPhoto;
    /**
     * ArrayList of String containing all the possible photo extension formats
     */
    private static String[] extensions = new String[]{"jpg", "png", "bmp", "gif"};
    /**
     * newTagBtn Button in Javafx
     */
    @FXML
    public Button newTagBtn;
    /**
     * helpBtn menu in Javafx
     */
    @FXML
    public Menu helpBtn;
    /**
     * loadHelpBtn menu in Javafx
     */
    @FXML
    public MenuItem loadHelpBtn;
    /**
     * viewHelpBtn menu in Javafx
     */
    @FXML
    public MenuItem viewHelpBtn;
    /**
     * manager links GUI to the background classes
     */
    private PhotoManager manager;
    /**
     * String storing the current working path that the user had chosen
     */
    private String curDir;
    /**
     * boolean to check the program has been through initialize or not
     */
    private boolean initalized = false;

    /**
     * Sets up the basic properties for the Graphics User Interface and resumes
     * User's work if they had already worked with our program
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        photoTab.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        avaTagListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        photoTagTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        photoTab.setEditable(true);
        photoTagTable.setEditable(true);
        fileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        tagsInTableCol.setCellValueFactory(new PropertyValueFactory<>("tags"));
        photoNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        ReadConfig.loadProperties();
        if (ReadConfig.getFirstTime()) {
            viewPhoto.setDisable(true);
            removeBtn.setDisable(true);
            loadBtn.setDisable(true);
        } else {
            curDir = ReadConfig.getDirectory();
            directLab.setText(curDir);
            manager = new PhotoManager(curDir);
            viewPhoto.setDisable(ReadConfig.getViewTab());
            if (ReadConfig.getSelectedTab().equals("ViewTab")) {
                photoPlane.getSelectionModel().select(viewPhoto);
            }
        }
    }

    /**
     * Function for chooseBtn Button in GUI. Allows User to pick a working directory, finds and
     * display a list of all image files under the path
     */
    public void chooseBtnAction() {
        photoTab.getItems().clear();
        DirectoryChooser dc = new DirectoryChooser();
        File mainDirectory = dc.showDialog(null);
        if (mainDirectory != null) {
            curDir = mainDirectory.getAbsolutePath();
            directLab.setText(curDir);
            manager = new PhotoManager(curDir);
            File[] listFiles = mainDirectory.listFiles();
            if (listFiles != null) {
                importHelper(listFiles);
            }
            removeBtn.setDisable(false);
            loadBtn.setDisable(false);
            viewPhoto.setDisable(false);
            PropertiesStorage.setDirectory(curDir);
            PropertiesStorage.setviewTab(false);
            PropertiesStorage.lastSelectedTab("LoadPhoto");
            PropertiesStorage.runConfig();
        }
    }

    /**
     * Function that help chooseBtnAction to import all required files under a directory and
     * add these files to the table.
     */
    private void importHelper(File[] listFiles) {
        for (File file : listFiles) {
            if (file.isDirectory() && file.listFiles() != null) {
                importHelper(file.listFiles());
            } else {
                for (String ext : extensions) {
                    String[] temp = file.getName().split("\\.(?=[^\\.]+$)");
                    if (!file.getName().contains("@") && !file.getName().contains(",")) {
                        if (temp[temp.length - 1].equals(ext)) {
                            String name = file.getName();
                            String path = file.getParent();
                            Photo photo = new Photo(name, path, "");
                            photoTab.getItems().add(photo);
                        }
                    }
                }
            }
        }
    }

    /**
     * Function for removeBtn Button in Load Photo Tab of the GUI. Allows User to remove photos they do
     * not want to work with under the directory
     */
    public void removeBtnAction() {
        ObservableList<Photo> photosSelected, allPhotos;
        photosSelected = photoTab.getSelectionModel().getSelectedItems();
        allPhotos = photoTab.getItems();
        allPhotos.removeAll(photosSelected);
    }

    /**
     * Read and Loads in all the photo under the working directory into our database , has the
     * ability to determine whether the photo had already added or not
     */
    public void loadBtnAction() {
        ObservableList<Photo> photos = photoTab.getItems();
        for (Photo photo : photos) {
            manager.loadPictures(photo);
        }
        photoTab.getItems().clear();
        manager.createDefaultTags();
        PropertiesStorage.setviewTab(false);
    }

    /**
     * A refresh function created to refresh the tableView within the GUI
     */

    public void viewImageBtnAction() {
        String curTag = tagChoiceBox.getSelectionModel().getSelectedItem();
        if (curTag != null) {
            photoTagTable.getItems().clear();
            ArrayList<Photo> photoInTag;
            manager.downloadPics();
            if (curTag.equals("All")) {
                photoInTag = manager.getCurrentPhotos();
            } else {
                photoInTag = manager.viewOneTag(curTag, curDir);
            }
            photoTagTable.getItems().addAll(photoInTag);
        }
    }

    /**
     * A helper function that refreshes the available tags whenever it is needed
     * to be updated
     */
    private void refreshAvaTagAction() {
        avaTagListView.getItems().clear();
        avaTagListView.getItems().addAll(manager.viewAllTags(curDir));
    }


    /**
     * A helper function that refreshes the items within the combo box whenever the items
     * were to be updated
     */
    private void refreshChoiceAction() {
        tagChoiceBox.getItems().clear();
        tagChoiceBox.getItems().add("All");
        tagChoiceBox.getItems().addAll(manager.viewAllTags(curDir));
        tagChoiceBox.getSelectionModel().selectFirst();
        viewImageBtnAction();
    }


    /**
     * method to be activated when the TableView is clicked in the "Load Picture" Tab
     * Displays the Image of the selected Photo
     */
    public void photoTabClicked() {
        photoTab.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                Photo photoSelected = photoTab.getSelectionModel().getSelectedItem();
                if (photoSelected != null) {
                    File file = new File(photoSelected.getLocation() + File.separator + photoSelected.getFullName());
                    imageView.setImage(new Image(file.toURI().toString()));
                }
            }
        });
    }


    /**
     * Creates a new Tag the user desired, passes the new Tag created to database
     * and calls the refresh helper methods to keep the lists and table views up to date
     *
     * reference for dialog:
     * http://code.makery.ch/blog/javafx-dialogs-official/
     */
    public void newTagBtnAction() {
        TextInputDialog newTag = new TextInputDialog();
        newTag.setTitle("Entering a new Tag");
        newTag.setContentText("Please Enter a new Tag: ");
        Optional<String> tagName = newTag.showAndWait();
        tagName.ifPresent(s -> manager.addNewTag(s, curDir));
        refreshAvaTagAction();
        refreshChoiceAction();
    }

    /**
     * Remove the selected tag out of the program. Rename any pictures that was previously in that tag
     */

    public void removeTagBtnAction() {
        String tagsSelected;
        tagsSelected = avaTagListView.getSelectionModel().getSelectedItem();
        manager.deleteTag(tagsSelected);
        refreshAvaTagAction();
        refreshChoiceAction();
    }

    /**
     * methods for addTag Button in the GUI. adds selected Tags to selected Photos.
     * Able to add multiple tags to multiple of photos in the same time or
     * perform single tag add
     */
    public void addTagToPhoto() {
        ObservableList<String> tagsSelected = avaTagListView.getSelectionModel().getSelectedItems();
        ObservableList<Photo> photoNameSelected = photoTagTable.getSelectionModel().getSelectedItems();
        if (!tagsSelected.isEmpty()) {
            if (!photoNameSelected.isEmpty()) {
                for (String tag : tagsSelected) {
                    for (Photo photo : photoNameSelected) {
                        manager.addTagToPhoto(tag, photo);
                    }
                }
            } else {
                ExceptionPrompter.showInvalidPhotoSelection();
            }
            viewImageBtnAction();
        } else {
            ExceptionPrompter.showInvalidTagSelection();
        }
    }

    /**
     * methods for removeTagsFromPhoto Button in the GUI. able to delete selected Tags to selected Photos.
     * Able to perform both single and multiple delete
     */

    public void setRemoveTagFromPhoto() {
        ObservableList<String> tagsSelected = avaTagListView.getSelectionModel().getSelectedItems();
        ObservableList<Photo> photoNameSelected = photoTagTable.getSelectionModel().getSelectedItems();
        if (!tagsSelected.isEmpty()) {
            if (!photoNameSelected.isEmpty()) {
                for (String tag : tagsSelected) {
                    for (Photo photo : photoNameSelected) {
                        manager.deleteTagInPhoto(tag, photo);
                    }
                }
            } else {
                ExceptionPrompter.showInvalidPhotoSelection();
            }
            viewImageBtnAction();
        } else {
            ExceptionPrompter.showInvalidTagSelection();
        }
    }

    /**
     * GUI Actions performed when the Tab is changed from "Load Photo"  to "ViewPhoto"
     * Refreshes items in "View Photo" Tab and updates properties for config
     */
    public void clickedViewTab() {
        refreshAvaTagAction();
        refreshChoiceAction();
        PropertiesStorage.setDirectory(curDir);
        PropertiesStorage.setviewTab(false);
        PropertiesStorage.lastSelectedTab("ViewTab");
        PropertiesStorage.runConfig();
    }

    /**
     * Displays the corresponding Image when user clicks on a photo in the "View Photo" Tab
     */
    public void clickedPhotoTagTable() {
        Photo photoSelected = photoTagTable.getSelectionModel().getSelectedItem();
        if (photoSelected != null) {
            File file = new File(photoSelected.getLocation() + File.separator + photoSelected.getFullName());
            displayPhoto.setImage(new Image(file.toURI().toString()));
        }
    }

    /**
     * Function for the "viewTagHistory" Button in GUI, opens a new fxml containing the
     * all the previous tags that the selected photo owned. User is allowed to replace
     * current tags with historical tags
     */
    public void clickViewHistory() throws Exception {
        Photo photo = photoTagTable.getSelectionModel().getSelectedItem();
        if (photo == null) {
            ExceptionPrompter.showInvalidPhotoSelection();
        } else {
            ViewHistory.historyTags = manager.viewHistoryTag(photo);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewHistory.fxml"));
            Parent root2 = fxmlLoader.load();
            Stage newStage = new Stage();
            newStage.setTitle("View History");
            newStage.setScene(new Scene(root2));
            newStage.showAndWait();
            if (ViewHistory.getSelectedTag() != null) {
                if (ViewHistory.getSelectedTag().equals("Empty")) {
                    manager.backHistory(photoTagTable.getSelectionModel().getSelectedItem(), " ");
                    ViewHistory.cleanTag();
                } else {
                    manager.backHistory(photoTagTable.getSelectionModel().getSelectedItem(), ViewHistory.getSelectedTag());
                    ViewHistory.cleanTag();
                }
            }
            refreshChoiceAction();
            refreshAvaTagAction();
        }
    }

    /**
     * Function for "viewTagHistory" Button in GUI, allows user to move a photo under the
     * current working directory to an new path they selected.
     */
    public void movePhoto() {
        ObservableList<Photo> photosSelected = photoTagTable.getSelectionModel().getSelectedItems();
        if (!photosSelected.isEmpty()) {
            String newPath;
            DirectoryChooser dc = new DirectoryChooser();
            File mainDirectory = dc.showDialog(null);
            if (photosSelected.size() == 0) {
                //exception
            } else if (mainDirectory != null) {
                newPath = mainDirectory.getAbsolutePath();
                for (Photo photo : photosSelected) {
                    manager.changePhotoLocation(photo, curDir, newPath);
                }
            }
            viewImageBtnAction();
        } else {
            ExceptionPrompter.showInvalidMove();
        }
    }


    /**
     * Help update the configuration properties when "Load Photo" Tab is clicked
     */
    public void loadPhotoAction() {
        if (initalized) {
            PropertiesStorage.lastSelectedTab("LoadPhoto");
            PropertiesStorage.setDirectory(curDir);
            PropertiesStorage.setviewTab(false);
            PropertiesStorage.runConfig();
        } else {
            initalized = true;
        }
    }

    /**
     * Pops the readme.txt when the Menu is clicked
     */
    public void helpBtnAction() throws IOException {
        File text = new File(System.getProperty("user.dir") + File.separator + "readme.txt");
        java.awt.Desktop.getDesktop().edit(text);
    }
}