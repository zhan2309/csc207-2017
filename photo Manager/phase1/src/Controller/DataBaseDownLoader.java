package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class DatabaseDownloader which is used to download data from database.
 */
class DataBaseDownLoader {

    /**
     * The connection used to connect to database.
     */
    private Connection connection;

    /**
     * Constructor of DataDownLoader.
     */
    DataBaseDownLoader() {
        connection = DataBaseConnection.dbConnector();
    }

    /**
     * Get specific attribute for a Photo from database such as the name of the Photo and the tags of
     * the Photo.
     *
     * @param imageName    String Name of the image.
     * @param property     String The column.
     * @param fileLocation String The location of picture.
     * @return String the attribute we want.
     */
    String getProperty(String imageName, String property, String fileLocation) {
        String sql =
                "SELECT "
                        + property
                        + " FROM photo WHERE name = '"
                        + imageName
                        + "'and location = '"
                        + fileLocation
                        + "';";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            return rs.getString(property);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get Pictures in which the first index is Photo name and the second index is Photo address in
     * one certain tag from database.
     *
     * @param workingPath String The path we are working at.
     * @param tag         String Tag of the file.
     * @return String Pictures.
     */
    String getPictures(String workingPath, String tag) {
        String sql =
                "SELECT pictures FROM tags WHERE name = '"
                        + tag
                        + "'and workingDirectory = '"
                        + workingPath
                        + "';";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // update
            ResultSet rs = preparedStatement.executeQuery();
            return rs.getString("pictures");
        } catch (SQLException e) {
            return "";
        }
    }

    /**
     * Get all tags which contains the same rootPath stored in a String ArrayList from the database.
     *
     * @param rootPath String Root path of the file.
     * @return ArrayList Tags we want.
     */
    public ArrayList<String> getAllTags(String rootPath) {

        ArrayList<String> temp = new ArrayList<>();
        String sql = "SELECT name FROM tags WHERE workingDirectory = '" + rootPath + "';";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // update
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                temp.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return temp;
    }

    /**
     * Generate photo class with the same rootPath including subDirectory by
     * downloading all photos information from the database.
     *
     * @param rootPath String main directory for those Photos that we want to construct.
     * @return ArrayList Photos.
     */
    public ArrayList<Photo> buildPhotos(String rootPath) {

        ArrayList<Photo> temp = new ArrayList<>();
        String sql = "SELECT name, location, tags FROM photo WHERE location LIKE '" + rootPath + "%'; ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String fileLocation = rs.getString("location");
                String tempTag = rs.getString("tags");
                if (tempTag.length() != 0 && tempTag.substring(tempTag.length() - 1).equals("|")) {
                    temp.add(new Photo(name, fileLocation, ""));
                } else {
                    String[] tags = tempTag.split("\\|");
                    Photo photo = new Photo(name, fileLocation, tags[tags.length - 1]);
                    temp.add(photo);
                }
            }
            return temp;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return temp;
    }
}
