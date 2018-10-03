package Controller;

import java.util.*;

/**
 * The Tag Observer to observe the Tags changes in the Photo.
 */
public class TagsObserver extends FileObserver {

    /**
     * Update the changes of Tags in this observable and update the changes in database.
     *
     * @param observable Photo selected Photo
     * @param arg        Object The argument collected by the observer
     */
    @Override
    public void update(Observable observable, Object arg) {
        //update tags on dataBase (adding tag and delete tag in one photo and delete tag table.
        if (arg instanceof String) {
            String temp = (String) arg;
            Photo photo = (Photo) observable;
            //adding tag
            if (temp.substring(0, 3).equals("at:")) {
                addHelper(temp, photo);
            } else if (temp.substring(0, 3).equals("dt:")) {
                deleteHelper(temp, photo);
            }
        }
    }

    /**
     * The add Helper to help the update method to add Tag in this observable.
     *
     * @param temp  String Tag
     * @param photo Photo the selected Photo
     */
    private void addHelper(String temp, Photo photo) {
        String addTag = temp.substring(3, temp.length());
        if (photo.getTags().equals("")) {
            photo.setTags(addTag);
        } else {
            String tag = photo.getTags();
            photo.setTags(tag + " " + addTag);
        }
    }

    /**
     * The add Helper to help the update method to delete Tag in this observable.
     *
     * @param temp  String Tag
     * @param photo Photo the selected Photo
     */
    private void deleteHelper(String temp, Photo photo) {
        String deleteTag = temp.substring(3, temp.length());
        ArrayList<String> arrS = photo.getTagsList();
        arrS.remove(deleteTag);
        StringBuilder stringBuilder = new StringBuilder();
        if (arrS.size() != 0) {
            for (int i = 0; i + 1 < arrS.size(); i++) {
                stringBuilder.append(arrS.get(i)).append(" ");
            }
            stringBuilder.append(arrS.get(arrS.size() - 1));
        }
        photo.setTags(stringBuilder.toString());
    }
}
