package Controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The Photo class that used to manage.
 */
public class Photo extends Observable {

    /**
     * fileName of a Photo
     */
    private String fileName;
    /**
     * oldFileName of a Photo
     */
    private String oldName;
    /**
     * Location of a Photo
     */
    private String location;
    /**
     * Tags of a Photo
     */
    private String tags;
    /**
     * Date of Photo
     */
    private String date;

    /**
     * stores the file's name, location, and tags
     *
     * @param fileName a String containing the original file name of the photo
     * @param location String containing the path to the designated photo
     * @param tags     a String containing all the tags the photo
     */
    public Photo(String fileName, String location, String tags) {
        this.fileName = fileName;
        this.location = location;
        this.tags = tags;
        this.oldName = "";
        Date date1 = new Date();
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1);
        this.addObserver(new LocationObserver());
        this.addObserver(new TagsObserver());
    }

    /**
     * getter method for the private variable fileName, provides a String containing fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * getter method for the private variable location, provides a String containing location
     */
    public String getLocation() {
        return location;
    }

    /**
     * getter method for the private variable tags, provides a String containing tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * setter method for the private variable location
     */
    public void setLocation(String location, String mainDirectory) {
        setChanged();
        ArrayList<String> changes = new ArrayList<>();
        changes.add(location);
        changes.add(mainDirectory);
        notifyObservers(changes);
        this.location = location;
    }

    /**
     * setter method for the private variable tags
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * getter method for the current date
     */
    String getDate() {
        return date;
    }

    /**
     * add tags to pre-existing tags in variable tag
     */
    void addTag(String tag) {
        oldName = getFullName();
        setChanged();
        notifyObservers("at:" + tag);
    }

    /**
     * deletes the input tags from the private tag variable
     */
    void deleteTag(String tag) {
        oldName = getFullName();
        setChanged();
        notifyObservers("dt:" + tag);
    }

    /**
     * returns the photo's most recent name with regard to the newly added or deleted tags
     */
    public String getFullName() {
        if (!tags.equals("")) {
            return tags + " " + fileName;
        } else {
            return fileName;
        }
    }

    /**
     * returns a String list of tags this photo current has
     */
    ArrayList<String> getTagsList() {
        String[] arr = tags.split(" +");
        return new ArrayList<>(Arrays.asList(arr));
    }

    /**
     * Update the path of the photo file after an adjustment is made
     */
    boolean renamePhoto() {
        String newName = getFullName();
        File photo = new File(location + File.separator + oldName);
        return photo.renameTo(new File(location + File.separator + newName));
    }


    /**
     * Return true iff only this is the same Photo.
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Photo)) {
            return false;
        }
        Photo photo = (Photo) o;
        return fileName.equals(((Photo) o).getFileName()) && location.equals(((Photo) o).getLocation()) &&
                tags.equals(((Photo) o).getTags());
    }
}