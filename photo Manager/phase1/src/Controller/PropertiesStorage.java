package Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * class that stores GUI properties
 * adapt the code from:
 * http://www.opencodez.com/java/write-config-file-in-java.htm
 */
public class PropertiesStorage {
    private static String directory;
    private static String selectedTab;
    private static String viewTab;

    /**
     * saving the most recent working directory
     */
    public static void setDirectory(String dir) {
        directory = dir;
    }

    /**
     * returns a boolean variable containing information telling whether it is the first
     */
    public static void lastSelectedTab(String tabName) {
        selectedTab = tabName;
    }

    /**
     * update whether the viewTab has been disabled
     */
    public static void setviewTab(boolean state) {
        if (state) {
            viewTab = "true";
        } else {
            viewTab = "false";
        }
    }

    /**
     * writes all the stored properties into the config file for later use
     */
    public static void runConfig() {
        Properties settings = new Properties();
        try {
            settings.setProperty("PhotoDirectory", directory);
            settings.setProperty("viewTabState", viewTab);
            settings.setProperty("lastSelectedTab", selectedTab);
            FileWriter writer = new FileWriter("config.properties");
            settings.store(writer, directory);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
