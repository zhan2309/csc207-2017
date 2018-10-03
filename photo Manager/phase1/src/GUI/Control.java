package GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that initialize the GUI of the program
 */
public class Control extends Application {

    // the main stage
    private Stage primaryStage;
    // the main pane of the stage
    private TabPane mainLayout1;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets up the layout of the main page
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Photo Manager");
        displayMainPage();
    }

    /**
     * Calls the MainPage.fxml and start the GUI
     */
    private void displayMainPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Control.class.getResource("MainPage.fxml"));
        mainLayout1 = loader.load();
        Scene scene = new Scene(mainLayout1);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }
}
