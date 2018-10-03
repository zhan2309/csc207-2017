package GUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewHistory extends MainPage implements Initializable {
    /**
     * shows the historical tag of a certain photo
     */
    @FXML
    public ListView<String> previousTags;
    /**
     * A button that performs the clickedSelectedTag method
     */
    @FXML
    public Button backToTag;
    /**
     * A button that performs the cancelBtn method when it is clicked
     */
    @FXML
    public Button cancelBtn;
    /**
     * returns a list of previous tags of the selected photo
     */
    static ArrayList<String> historyTags;
    /**
     * returns the selected Historical tag the user had chosen
     */
    private static String selectedTag;

    /**
     * Sets up the basic properties for the ViewHistory GUI
     *
     * @param location  URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (String history : historyTags) {
            if (history.equals(" ")) {
                previousTags.getItems().add("Empty");
            } else {
                previousTags.getItems().add(history);
            }
        }
    }

    /**
     * set the selected Tag into null
     */
    static void cleanTag() {
        selectedTag = null;
    }

    /**
     * records the historical tag selected and close the stage
     */
    public void clickSelectedTag() {
        selectedTag = (String) previousTags.getSelectionModel().getSelectedItem();
        Stage stage = (Stage) backToTag.getScene().getWindow();
        stage.close();
    }

    /**
     * returns the tag that is currently selected
     */
    static String getSelectedTag() {
        return selectedTag;
    }

    /**
     * closes the stage
     */
    public void cancelBtn() {
        selectedTag = null;
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
