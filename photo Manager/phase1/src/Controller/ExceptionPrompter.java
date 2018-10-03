package Controller;


import javax.swing.*;

/**
 * Class that manages the exception prompts within the program
 */
public class ExceptionPrompter {
    /**
     * methods that helps to create a prompt dialog when exception is raised
     */
    private static void showInvalidHelper(String message, String warningTitle) {
        JOptionPane.showMessageDialog(null,
                message,
                warningTitle, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Exception to be called when the user trying to create a tag containing space
     */
    public static void showInvalidSpaceTagMes() {
        showInvalidHelper(
                "The tag cannot contain space.", "Invalid Tag");
    }

    /**
     * Exception to be called when the user trying to create an empty tag
     */
    public static void showInvalidEmptyTagMes() {
        showInvalidHelper(
                "The tag cannot be Empty.", "Invalid Tag");
    }

    /**
     * Exception to be called when the user trying delete a tag that the photo does not have
     */
    public static void showNotExistTagMes() {
        showInvalidHelper(
                "The selected tag is not in this current photo.", "Invalid Tag");
    }

    /**
     * Exception to be called when the user is trying to load in photo that has already been loaded
     */
    public static void showInvalidLoad(Photo photo) {
        showInvalidHelper(
                "File " + photo.getFullName() + " has been loaded already.", "Invalid Load");
    }

    /**
     * Exception to be called when the user is trying to move a photo to a directory that has another photo
     * with the exact same name
     */
    public static void showDuplicateFileMes() {
        showInvalidHelper("Destination folder contains file with the same name.", "Invalid Move");
    }

    /**
     * Exception to be called when the user is trying to perform an invalid move
     */
    public static void showInvalidMove() {
        showInvalidHelper(
                "Invalid Move!", "Invalid Move");
    }

    /**
     * Exception to be called when the user is trying to perform actions without selecting
     * a photo
     */
    public static void showInvalidPhotoSelection() {
        showInvalidHelper(
                "Please select a photo first.",
                "Invalid Photo Selection");
    }

    /**
     * Exception to be called when the user is trying to perform actions without selecting a tag
     */
    public static void showInvalidTagSelection() {
        showInvalidHelper("Please select a Tag first", "Invalid Tag Selection");
    }

}
