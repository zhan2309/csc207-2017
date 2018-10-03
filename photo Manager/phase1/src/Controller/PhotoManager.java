package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

/**
 * The PhotoManager used to Manage all photo class.
 */
public class PhotoManager {

    /**
     * Logger to record the changes for photoManager
     */
    private static final Logger LOGGER = Logger.getLogger(PhotoManager.class.getName());

    /**
     * The container to store all photo class
     */
    private ArrayList<Photo> currentPhotos;

    /**
     * Class of DataBaseUpdater to modify database.
     */
    private DataBaseUpdater dataBaseUpdater;

    /**
     * Class DatabaseDownloader which is used to download data from database.
     */
    private DataBaseDownLoader dataBaseDownLoader;

    /**
     * The working directory of the photoManager.
     */
    private String rootPath;

    /**
     * The constructor of PhotoManager class @param rootPath String the WorkingDirectory.
     */
    public PhotoManager(String rootPath) {
        dataBaseUpdater = new DataBaseUpdater();
        dataBaseDownLoader = new DataBaseDownLoader();
        this.rootPath = rootPath;
        FileHandler fh;
        try {
            fh = new FileHandler("PhotoManager.log", false);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
            return;
        }
        fh.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fh);
        LOGGER.setLevel(Level.ALL);
    }

    /**
     * add default tags into the database in this workingDirectory.
     */
    public void createDefaultTags() {
        dataBaseUpdater.addTagData("@Handsome", this.rootPath);
        dataBaseUpdater.addTagData("@Beautiful", this.rootPath);
        dataBaseUpdater.addTagData("@Strong", this.rootPath);
        dataBaseUpdater.addTagData("@Weak", this.rootPath);
    }

    /**
     * Get all photos from database in this workingDirectory.
     */
    public void downloadPics() {
        currentPhotos = dataBaseDownLoader.buildPhotos(rootPath);
    }

    /**
     * Return the current Photos @return ArrayList current Photos.
     */
    public ArrayList<Photo> getCurrentPhotos() {
        return currentPhotos;
    }

    /**
     * Update the selected Photo class @param photo Photo.
     */
    public void loadPictures(Photo photo) {
        dataBaseUpdater.addPhotoData(photo);
    }

    /**
     * Add an New Tag in this Photo Mangaer System.
     *
     * @param newTag   String The new Tag.
     * @param rootPath String The workingDirectory.
     */
    public void addNewTag(String newTag, String rootPath) {

        if (!newTag.equals("")) {
            if (!newTag.contains(" ")) {

                if (!newTag.substring(0, 1).equals("@")) {
                    newTag = "@" + newTag;
                }

                dataBaseUpdater.addTagData(newTag, rootPath);
            } else {
                ExceptionPrompter.showInvalidSpaceTagMes();
            }

        } else {
            ExceptionPrompter.showInvalidEmptyTagMes();
        }
    }

    /**
     * Delete the selected Tag  @param tag String The Tag we want to delete.
     */
    public void deleteTag(String tag) {

        ArrayList<Photo> photos = dataBaseDownLoader.buildPhotos(rootPath);
        for (Photo onePhoto : photos) {
            deleteTagInPhoto(tag, onePhoto);
        }
        dataBaseUpdater.deleteTagTuple(rootPath, tag);
    }

    /**
     * View all current Tags under this workingDirectory.
     *
     * @param rootPath String The working Directory in the photo Manager System.
     * @return ArrayList All tags.
     */
    public ArrayList<String> viewAllTags(String rootPath) {
        return dataBaseDownLoader.getAllTags(rootPath);
    }

    /**
     * Add a Tag in a Photo Class.
     *
     * @param tag   String The selected Tag.
     * @param photo Photo The photo that we want to add tag into.
     */
    public void addTagToPhoto(String tag, Photo photo) {
        if (!(photo.getTagsList().contains(tag))) {
            photo.addTag(tag);
            photo.renamePhoto();
            dataBaseUpdater.updateHistory(photo);
            // need change the data in tags table as well and change the real PhotoName as well in tags observer
            dataBaseUpdater.updatePictures(photo, rootPath, tag, "add");
            LOGGER.info("The Tag: " + tag + " has been added into the Photo: " + photo.getFileName());
        }
    }

    /**
     * Delete the selected tag in the selected Photo class.
     *
     * @param tag   String The Selected Tag.
     * @param photo Photo The selected Photo.
     */
    public void deleteTagInPhoto(String tag, Photo photo) {

        if (photo.getTagsList().contains(tag)) {
            photo.deleteTag(tag);
            photo.renamePhoto();
            dataBaseUpdater.updateHistory(photo);
            // delete the pictures in tags table as well and change the real PhotoName as well in tags observer
            dataBaseUpdater.updatePictures(photo, rootPath, tag, "delete");
            LOGGER.info("The Tag: " + tag + "has been deleted from the Photo: " + photo.getFileName());
        }
    }

    /**
     * Return the selected Photo history.
     *
     * @param photo Photo The selected Photo.
     * @return ArrayList The history of a Photo.
     */
    public ArrayList<String> viewHistoryTag(Photo photo) {
        ArrayList<String> historyTags = new ArrayList<>();
        String history = dataBaseDownLoader.getProperty(photo.getFileName(), "tags", photo.getLocation());
        if (history.length() > 0) {
            if (history.substring(history.length() - 1).equals("|")) {
                history = history + " ";
            }
            String[] temp = history.split("\\|");
            historyTags.addAll(Arrays.asList(temp).subList(0, temp.length - 1));
        }

        return historyTags;
    }

    /**
     * Back the selected Photo into a previous Tag that it contains.
     *
     * @param photo Photo The Selected Photo.
     * @param tags  String The Tag wanted to go back
     */
    public void backHistory(Photo photo, String tags) {

        String oldTag = photo.getTags();
        ArrayList<String> temp = photo.getTagsList();
        for (String tag : temp) {
            photo.deleteTag(tag);
            photo.renamePhoto();
            dataBaseUpdater.updatePictures(photo, rootPath, tag, "delete");
        }

        if (!tags.equals(" ")) {
            String[] split = tags.split(" +");
            List<String> splitTags = new ArrayList<>(Arrays.asList(split));
            for (String item : splitTags) {
                photo.addTag(item);
                photo.renamePhoto();
                dataBaseUpdater.addTagData(item, rootPath);
                dataBaseUpdater.updatePictures(photo, rootPath, item, "add");
            }
        }

        LOGGER.info("The Photo with file name: " + photo.getFileName() +
                " has change to Tags: FROM " + oldTag + " TO " + photo.getTags());
        dataBaseUpdater.updateHistory(photo);
    }

    /**
     * Return a ArrayList of Photo class in order to view all Photo class under this working Directory and Selected Tag.
     *
     * @param tag      String The selected Tag
     * @param rootPath String The working Directory
     * @return ArrayList The Photos under this workingDirectory and Tag
     */
    public ArrayList<Photo> viewOneTag(String tag, String rootPath) {
        String temp = dataBaseDownLoader.getPictures(rootPath, tag);
        ArrayList<Photo> photos = new ArrayList<>();
        String[] split = temp.split("\\|");
        if (!split[0].equals("")) {
            for (String pic : split) {
                String[] tempPic = pic.split(",");
                String allTagHistory = dataBaseDownLoader.getProperty(tempPic[0], "tags", tempPic[1]);
                String[] allTagArray = allTagHistory.split("\\|");
                String currentTag = allTagArray[allTagArray.length - 1];
                photos.add(new Photo(tempPic[0], tempPic[1], currentTag));
            }
        }
        return photos;
    }

    /**
     * Move the Photo from the old directory to a new Directory.
     *
     * @param photo         Photo The selected Photo
     * @param mainDirectory String The old Directory
     * @param newDirectory  String The new Directory
     */
    public void changePhotoLocation(Photo photo, String mainDirectory, String newDirectory) {

        photo.setLocation(newDirectory, mainDirectory);
    }
}
