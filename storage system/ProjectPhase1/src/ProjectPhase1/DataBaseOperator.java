package ProjectPhase1;

import java.sql.*;

/**
 * This class is used to make operations to the database.
 */
public class DataBaseOperator {

    // Connect to DataBase.
    public static Connection connection = SqliteConnection.dbConnector();


    /**
     * Insert a entry of data to the DataBase.
     * @param inventoryName String
     * @param goods Good
     */
    public static void insertGood(String inventoryName, Goods goods) {
        String sql = "INSERT INTO "+ inventoryName +"(upc," +
                "name,quantity,price,location,cost,threshold) VALUES(?,?,?,?,?,?,?)";
        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, goods.getUpc());
                preparedStatement.setString(2, goods.getName());
                preparedStatement.setInt(3, goods.getQuantity());
                preparedStatement.setDouble(4,goods.getPrice());
                preparedStatement.setString(5, goods.getLocation());
                preparedStatement.setDouble(6, goods.getCost());
                preparedStatement.setInt(7, goods.getThreshold());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("DataBase Connection failed");
        }
    }

    /**
     * Insert a order to the database.
     * @param order Order order to insert
     */
    public static void insertOrder( Order order) {
        String sql = "INSERT INTO Orders (orderNumber,upc,name,isPending,quantity) VALUES(?,?,?,?,?)";
        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1,order.getOrderNumber());
                preparedStatement.setString(2, order.getUpc());
                preparedStatement.setString(3, order.getName());
                preparedStatement.setString(4, order.getIsPending());
                preparedStatement.setInt(5,order.getQuantity());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("DataBase Connection failed");
        }
    }

    /**
     * Insert a good to a specific inventory.
     * @param upc String upc of the good.
     * @param inventoryName String inventory to insert.
     */
    public static void insertInventoryName(String upc, String inventoryName) {
        String sql = "INSERT INTO InventoryName (upc,inventory) VALUES(?,?)";
        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, upc);
                preparedStatement.setString(2, inventoryName);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("DataBase Connection failed");
        }
    }

    /**
     * Set a period of on sale time to a good.
     * @param upc String upc of the good.
     * @param discount Double discount
     */
    public static void insertOnSaleTime(String upc, double discount) {

        String date = Helpers.getSystemTime();
        String inventoryName = DataBaseViewer.getInventoryName(upc);
        String name = DataBaseViewer.getString(upc, inventoryName, "name");
        String sql = "INSERT INTO OnSaleTime (time,upc,name,discount) VALUES(?,?,?,?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, upc);
            preparedStatement.setString(3, name);
            preparedStatement.setDouble(4,discount);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Set the price during a certain time of a good.
     * @param upc String upc of the good.
     * @param price double price to set.
     */
    public static void insertPriceHistory(String upc, double price) {

        String date = Helpers.getSystemTime();
        String inventoryName = DataBaseViewer.getInventoryName(upc);
        String name = DataBaseViewer.getString(upc, inventoryName, "name");
        String sql = "INSERT INTO PriceHistory (time,upc,name,price) VALUES(?,?,?,?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, upc);
            preparedStatement.setString(3, name);
            preparedStatement.setDouble(4,price);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Update the information of sale amount of a good.
     * @param upc String upc of the good.
     * @param quantity int quantity of sale.
     */
    public static void insertSaleHistory(String upc, int quantity) {
        String date = Helpers.getSystemTime();
        String inventoryName = DataBaseViewer.getInventoryName(upc);
        String name = DataBaseViewer.getString(upc, inventoryName, "name");
        double soldPrice = DataBaseViewer.getDouble(upc,inventoryName,"price");
        String sql = "INSERT INTO SaleHistory (time,upc,name,quantity,soldPrice) VALUES(?,?,?,?,?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, upc);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4,quantity);
            preparedStatement.setDouble(5,soldPrice);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Update the quantity of a good.
     * @param upc String upc of the good.
     * @param quantity int quantity to update.
     */
    public static void updateQuantity(String upc, int quantity) {
        String inventoryName = DataBaseViewer.getInventoryName(upc);
        String sql = "UPDATE " + inventoryName + " SET quantity = ? where upc = "+upc ;

        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                // set the corresponding param
                preparedStatement.setInt(1, quantity);
                // update
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("DataBase Connection failed");
        }
    }

    public static void updatePrice(String upc, double price) {
        String inventoryName = DataBaseViewer.getInventoryName(upc);
        String sql = "UPDATE " + inventoryName + " SET price = ? where upc = "+upc ;

        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                // set the corresponding param
                preparedStatement.setDouble(1, price);
                // update
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Update the location of a good.
     * @param upc String upc of the good.
     * @param location String location of the good.
     */
    public static void updateLocation(String upc, String location) {
        String inventoryName = DataBaseViewer.getInventoryName(upc);
        String sql = "UPDATE "+ inventoryName +" SET location = ? where upc = "+upc ;

        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                // set the corresponding param
                preparedStatement.setString(1, location);
                // update
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("DataBase Connection failed");
        }
    }


    /**
     * Create a table display all the information of a good.
     * @param name name of the good.
     */
    public static void createTable(String name) {

        String sql = "CREATE TABLE IF NOT EXISTS "+ name + "(\n"
                + "	upc text ,\n"
                + "	name text ,\n"
                + "	quantity integer,\n"
                + "	price double,\n"
                + "	location text,\n"
                + "	cost double,\n"
                + "	threshold integer\n"
                + ");";

        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // update
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("DataBase Connection failed");
        }
    }

}
