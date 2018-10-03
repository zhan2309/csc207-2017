package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Observable;

/**
 * The Location Observer to observe the location changes for the observable
 */
public class LocationObserver extends FileObserver {

    /**
     * Update the Location changes to the DataBase.
     *
     * @param arg Object The argument collected by the observer
     */
    @Override
    public void update(Observable observable, Object arg) {
        //updateLocation on dataBase
        Photo photo = (Photo) observable;
        if (arg instanceof ArrayList) {
            ArrayList temp = (ArrayList) arg;
            String newLocation = (String) temp.get(0);
            String mainDir = (String) temp.get(1);
            Path mainPath = Paths.get(mainDir);
            Path sourcePath = Paths.get(photo.getLocation() + File.separator + photo.getFullName());
            Path newPath = Paths.get(newLocation + File.separator + photo.getFullName());
            if (!Files.exists(newPath)) {
                ArrayList<String> tags = photo.getTagsList();
                if (newPath.startsWith(mainPath)) {
                    innerMoveHelper(tags, photo, mainDir, newLocation);
                } else {
                    outMoveHelper(tags, photo, mainDir, newLocation);
                }
                try {
                    Files.move(sourcePath, newPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    ExceptionPrompter.showInvalidMove();

                }
            } else {
                ExceptionPrompter.showDuplicateFileMes();
            }
        }
    }

    /**
     * The help method to help the update method to move a Photo into a subdirectory file.
     *
     * @param tags        ArrayList tags that this Photo contains
     * @param photo       Photo The Selected Photo
     * @param mainDir     String The Old directory path
     * @param newLocation String The new directory path
     */
    private void innerMoveHelper(ArrayList<String> tags, Photo photo, String mainDir, String newLocation) {
        System.out.println(1);
        if (!tags.get(0).equals("")) {
            for (String tag : tags) {
                dataBaseUpdater.updatePictures(photo, mainDir, tag,
                        "cl:" + newLocation);
            }
        }
        dataBaseUpdater.update(photo, "location", newLocation);
    }

    /**
     * The help method to help the update method to move a Photo into outside directory file.
     *
     * @param tags        ArrayList tags that this Photo contains
     * @param photo       Photo The Selected Photo
     * @param mainDir     String The Old directory path
     * @param newLocation String The new directory path
     */
    private void outMoveHelper(ArrayList<String> tags, Photo photo, String mainDir, String newLocation) {
        if (!tags.get(0).equals("")) {
            for (String tag : tags) {
                dataBaseUpdater.updatePictures(photo, mainDir, tag, "delete");
                dataBaseUpdater.addTagData(tag, newLocation);
                dataBaseUpdater.updatePictures(photo, newLocation, tag, "add");
            }
        }
        dataBaseUpdater.update(photo, "location", newLocation);
    }
}
