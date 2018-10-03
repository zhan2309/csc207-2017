package ProjectPhase1;

import javax.swing.*;

import static java.io.File.separator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used when user is logged in as a type of reshelver.
 */
public class Reshelver {

    // Connect to DataBase.
    private static Connection connection = SqliteConnection.dbConnector();

    /**
     * This method is used to view product location with given upc.
     * @param upc given upc
     * @return location of product
     */
    public String viewProductLocation(String upc)  {

        String inventoryName = DataBaseViewer.getInventoryName(upc);
        return DataBaseViewer.getString(upc,inventoryName, "location");
    }

    /**
     * This method is used to view order history of product with given upc
     * @param upc given upc
     */
    public void viewProductOrderHistory(String upc)  {
        String sql = "SELECT rowid, name, isPending, quantity"
                + " FROM Orders where upc = "+ upc;
        String temp = "";
        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                if (DataBaseViewer.loopColumnForString("upc", upc, "Orders")) {
                    // update
                    ResultSet rs = preparedStatement.executeQuery();
                    String id = rs.getString("rowid");
                    String name = rs.getString("name");
                    String isPending = rs.getString("isPending");
                    int quantity = rs.getInt("quantity");
                    temp += "OrderNumber:" + id + " | " + "upc: " + upc + " | " + "GoodName: " +
                            name + " | " + "Pending: " + isPending + " | " + "Quantity: " + quantity;
                    JOptionPane.showMessageDialog(null, temp);
                } else {
                    Helpers.showInvalidUpc();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * This method is used to view all order history of the store.
     */
    public void viewAllOrderHistory() {
        DataBaseShowTable dataBaseShowTable = new DataBaseShowTable("Orders");
    }


    /**
     * This method is used to view quantity of good with given upc.
     * @param upc given upc
     * @return quantity of good
     */
    public int viewQuantity(String upc){
        String inventoryName = DataBaseViewer.getInventoryName(upc);
        return DataBaseViewer.getInt(upc, inventoryName, "quantity");
    }

    /**
     * Modify the location of the Goods.
     * @param upc String upc
     * @param location String the location of the goods
     */
    public void changeLocation(String upc, String location){
        DataBaseOperator.updateLocation(upc, location);
    }

}

