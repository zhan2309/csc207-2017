package ProjectPhase1;

import java.sql.*;

/**
 * Class used to access to all the information in the database.
 */
public class DataBaseViewer {

    // Connect to DataBase.
    private static Connection connection = SqliteConnection.dbConnector();

    /**
     * Get all the string information.
     * @param upc String
     * @param table String
     * @param thing String
     * @return String value need to accessed.
     */
    public static String getString(String upc, String table, String thing){

        String sql = "SELECT "+ thing +" from "+ table + " where upc = "+upc ;

        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // update
                ResultSet rs = preparedStatement.executeQuery();

                return rs.getString(thing);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Get all the int information in the database.
     * @param upc String
     * @param table String
     * @param thing String
     * @return int All int information.
     */
    public static int getInt(String upc, String table, String thing){

        String sql = "SELECT "+ thing +" from "+ table + " where upc = "+upc ;

        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // update
                ResultSet rs = preparedStatement.executeQuery();

                return rs.getInt(thing);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * Get all double information.
     * @param upc String
     * @param table String
     * @param thing String
     * @return double all double information.
     */
    public static double getDouble(String upc, String table, String thing){

        String sql = "SELECT "+ thing+" from "+ table + " where upc = "+upc ;

        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // update
                ResultSet rs = preparedStatement.executeQuery();

                return rs.getDouble(thing);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * Get name of the inventory.
     * @param upc String
     * @return String inventory
     */
    public static String getInventoryName(String upc){

        String sql = "SELECT inventory from InventoryName where upc = "+ upc ;

        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // update
                ResultSet rs = preparedStatement.executeQuery();

                return rs.getString("inventory");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        } return null;
    }

    /**
     * Helper.
     * @param column String
     * @param thing String
     * @param table String
     * @return Boolean
     */
    public static boolean loopColumnForString(String column, String thing, String table)  {
        String query = "SELECT " + column
                + " FROM "+ table;
        boolean found = false;

        try (Statement stmt  = connection.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){

            // loop through the result set
            while (rs.next()) {
                String oneStringData = rs.getString(column);
                if (oneStringData.equals(thing)){
                    found = true;
                }
            }
            return found;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return found;
        }
    }

    /**
     * Helper.
     * @param thing String
     * @param rowId String
     * @param table String
     * @return String
     */
    public static String loopRowIdToGetString(String thing, int rowId, String table)  {
        String query = "SELECT rowid, "+ thing
                + " FROM "+ table;
        String temp = "";

        try (Statement stmt  = connection.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){

            // loop through the result set
            while (rs.next()) {
                int oneStringData = rs.getInt("rowid");
                String newthing = rs.getString(thing);
                if (oneStringData == rowId){
                    temp += newthing;
                }
            }
            return temp;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
