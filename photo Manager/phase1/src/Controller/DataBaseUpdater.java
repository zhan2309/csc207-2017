package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class of DataBaseUpdater to modify database.
 */
public class DataBaseUpdater {

    /**
     * The connection used to connect to database.
     */
    private Connection connection;

    /**
     * DataBaseDownLoader used to get Photo data from the database.
     */
    private DataBaseDownLoader dataBaseDownLoader;

    /**
     * The constructor of DatabaseUpdater.
     */
    public DataBaseUpdater() {
        connection = DataBaseConnection.dbConnector();
        dataBaseDownLoader = new DataBaseDownLoader();
    }

    /**
     * Update the Photo attributes in database.
     *
     * @param photo    String The photo we choose.
     * @param property String the attribute we choose.
     * @param content  String the new content that we want to update.
     */
    public void update(Photo photo, String property, String content) {

        String sql =
                "UPDATE photo SET " + property + " = ? WHERE name = '" + photo.getFileName() + "';";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // set the corresponding param
            preparedStatement.setString(1, content);
            // update
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Update the pictures in tags table including changing location, adding tag and deleting tags.
     *
     * @param photo       String The photo we choose.
     * @param workingPath String The path we will work at.
     * @param tag         String the tag we need to update.
     * @param command     String The operation code.
     */
    public void updatePictures(Photo photo, String workingPath, String tag, String command) {
        String sql =
                "UPDATE tags SET pictures = ? WHERE workingDirectory = '"
                        + workingPath
                        + "' and name = '"
                        + tag
                        + "';";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            String temp = "";
            String oldPictures = dataBaseDownLoader.getPictures(workingPath, tag);
            // add tag to tags table.
            if (command.equals("add")) {
                temp = addHelper(oldPictures, photo);
            }
            // delete Tag in tags table
            else if (command.equals("delete")) {
                temp = deleteHelper(oldPictures, photo);

            } else if (command.substring(0, 3).equals("cl:")) {
                temp = clHelper(command, oldPictures, photo);
            }
            // set the corresponding param
            preparedStatement.setString(1, temp);
            // update
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Helper method to help updatePictures in order to add photo info in tags in database.
     *
     * @param oldPictures String the old photo fileName and old photo location
     * @param photo       Photo that we want to add into tags table in database
     * @return String template we wanted
     */
    private String addHelper(String oldPictures, Photo photo) {
        if (oldPictures.equals("")) {
            // update pictures when there is no a picture
            return photo.getFileName() + "," + photo.getLocation() + "|";
        } else {
            // update pictures when there is already a picture
            return oldPictures + photo.getFileName() + "," + photo.getLocation() + "|";
        }
    }

    /**
     * Helper method to help updatePictures in order to delete photo info in tags in database.
     *
     * @param oldPictures String the old photo fileName and old photo location
     * @param photo       Photo that we want to add into tags table in database
     * @return String template we wanted
     */
    private String deleteHelper(String oldPictures, Photo photo) {
        StringBuilder temp = new StringBuilder();
        String needDeletePicture = photo.getFileName() + "," + photo.getLocation();
        String[] splitPics = oldPictures.split("\\|");
        for (String picture : splitPics) {
            if (!picture.equals(needDeletePicture)) {
                temp.append(picture).append("|");
            }
        }
        return temp.toString();
    }

    /**
     * Helper method to help updatePictures in order to change photo location in tags in database.
     *
     * @param oldPictures String the old photo fileName and old photo location
     * @param photo       Photo that we want to add into tags table in database
     * @return String template we wanted
     */
    private String clHelper(String command, String oldPictures, Photo photo) {
        String newLocation = command.substring(3, command.length() - 1);
        String oldLocation = photo.getLocation();
        return oldPictures.replace(oldLocation, newLocation);
    }

    /**
     * Record the PhotoData into the photo table in database.
     *
     * @param photo Photo that we want to add into photo table in database
     */
    public void addPhotoData(Photo photo) {
        String sql = "INSERT INTO photo(name,location,tags,date) VALUES(?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, photo.getFileName());
            preparedStatement.setString(2, photo.getLocation());
            preparedStatement.setString(3, photo.getTags());
            preparedStatement.setString(4, photo.getDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            ExceptionPrompter.showInvalidLoad(photo);
        }
    }

    /**
     * Record the new Tag data info inserting into the tag table in database.
     *
     * @param tag         String The tag we want to add into tag table in database
     * @param workingPath String that we want to store in which workingPath
     */
    public void addTagData(String tag, String workingPath) {

        if (!tagExist(tag, workingPath)) {
            String sql = "INSERT INTO tags(name, workingDirectory,pictures) VALUES(?,?,?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, tag);
                preparedStatement.setString(2, workingPath);
                preparedStatement.setString(3, "");
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Return True iff the selected Tag is in the tags table in the database.
     *
     * @param tag         String The Tag we selected
     * @param workingPath String the workingPath the selected located
     * @return boolean
     */
    private boolean tagExist(String tag, String workingPath) {
        String sql = "SELECT name,workingDirectory FROM tags;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // update
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if (rs.getString("name").equals(tag)
                        && rs.getString("workingDirectory").equals(workingPath)) {
                    return true;
                }
            }

            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    /**
     * Delete the Tag tuple in the tags table in database.
     *
     * @param workingPath String the workingPath the selected located
     * @param tag         String The Tag we selected
     */
    public void deleteTagTuple(String workingPath, String tag) {
        if (tagExist(tag, workingPath)) {
            String sql =
                    "DELETE FROM tags WHERE name = '"
                            + tag
                            + "' AND workingDirectory = '"
                            + workingPath
                            + "';";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Update the tag history for selected Photo into the tags table in database.
     *
     * @param photo Photo that we selected for updating History.
     */
    public void updateHistory(Photo photo) {
        String currentTag =
                dataBaseDownLoader.getProperty(photo.getFileName(), "tags", photo.getLocation());
        if (currentTag.length() == 0 || currentTag.substring(currentTag.length() - 1).equals("|")) {
            update(photo, "tags", currentTag + " |" + photo.getTags());
        } else {
            update(photo, "tags", currentTag + "|" + photo.getTags());
        }
    }
}
