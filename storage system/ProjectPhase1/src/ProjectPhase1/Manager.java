package ProjectPhase1;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the class of Manager who could review status of the store.
 */
class Manager {


  // Connect to DataBase.
  private static Connection connection = SqliteConnection.dbConnector();

  /**
   * Return an ArrayList containing all names of the PendingOrders in the Orders.
   *
   * @return ArrayList
   */
  void viewOrders() {
    DataBaseShowTable dataBaseShowTable = new DataBaseShowTable("Orders");
  }


  void viewSaleHistory(){
    DataBaseShowTable dataBaseShowTable = new DataBaseShowTable("SaleHistory");
  }

  /**
   * Return the DailyTotalRevenue on one certain date.
   *
   * @param upc  String the upc of that Goods
   * @param date String The selling date
   * @return double The DailyTotalRevenue
   */
  double viewDailyTotalRevenue(String upc, String date) {
    String query = "SELECT time, upc, quantity, soldPrice"
            + " FROM SaleHistory";
    double temp = 0;

    try (Statement stmt  = connection.createStatement();
         ResultSet rs    = stmt.executeQuery(query)){

      // loop through the result set
      while (rs.next()) {
        String oneStringData = rs.getString("time");
        int soldVolume = rs.getInt("quantity");
        double number = rs.getDouble("soldPrice");
        String goodUpc = rs.getString("upc");
        if (oneStringData.contains(date) && goodUpc.equals(upc)){
          temp += number*soldVolume;
        }
      }
      return temp;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return 0;
    }
  }

  /**
   * Return the DailyTotalProfit on one certain date.
   *
   * @param upc  String the upc of that Goods
   * @param date String The selling date
   * @return double The DailyTotalProfit
   */
  double viewDailyTotalProfit(String upc, String date) {
    String query = "SELECT time, upc, quantity, soldPrice"
            + " FROM SaleHistory";
    double temp = 0;

    try (Statement stmt  = connection.createStatement();
         ResultSet rs    = stmt.executeQuery(query)){

      // loop through the result set
      while (rs.next()) {
        String oneStringData = rs.getString("time");
        int soldVolume = rs.getInt("quantity");
        double number = rs.getDouble("soldPrice");
        String goodUpc = rs.getString("upc");
        if (oneStringData.contains(date) && goodUpc.equals(upc)){
          String inventory = DataBaseViewer.getInventoryName(upc);
          double cost = DataBaseViewer.getDouble(upc,inventory,"cost");
          temp += (number-cost)*soldVolume;
        }
      }
      return temp;

    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return 0;
    }
  }

  /**
   * View the daily total sale amount of all goods.
   * @param date String
   * @return int
   */
  int VDTotalSalesVolume(String date){
    String query = "SELECT time, quantity"
            + " FROM SaleHistory";
    int temp = 0;

    try (Statement stmt  = connection.createStatement();
         ResultSet rs    = stmt.executeQuery(query)){

      // loop through the result set
      while (rs.next()) {
        String oneStringData = rs.getString("time");
        int soldVolume = rs.getInt("quantity");
        if (oneStringData.contains(date)){
          temp += soldVolume;
        }
      }
      return temp;

    } catch (SQLException e) {
      Helpers.showInVaildMessage();
      return 0;
    }
  }

  /**
   * View daily total sale amount of a specific good.
   * @param upc String
   * @param date String
   * @return int
   */
  int VDSingleGoodSalesVolume(String upc, String date){
    String query = "SELECT time, upc, quantity"
            + " FROM SaleHistory";
    int temp = 0;

    try (Statement stmt  = connection.createStatement();
         ResultSet rs    = stmt.executeQuery(query)){

      // loop through the result set
      while (rs.next()) {
        String oneStringData = rs.getString("time");
        int soldVolume = rs.getInt("quantity");
        String upcName = rs.getString("upc");
        if (oneStringData.contains(date) && upcName.equals(upc)){
          temp += soldVolume;
        }
      }
      return temp;

    } catch (SQLException e) {
      Helpers.showInVaildMessage();
      return 0;
    }
  }

  /**
   * Set sale price of a good.
   * @param upc String
   * @param discount Double
   */
  void setSalePrice(String upc, Double discount) {
    String inventory = DataBaseViewer.getInventoryName(upc);
    double oldPrice = DataBaseViewer.getDouble(upc,inventory,"price");
    DataBaseOperator.updatePrice(upc,oldPrice*(1-discount));
    DataBaseOperator.insertOnSaleTime(upc,discount);

  }

}