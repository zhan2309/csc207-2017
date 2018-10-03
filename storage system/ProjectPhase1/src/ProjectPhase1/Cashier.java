package ProjectPhase1;

import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;

import static java.io.File.separator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * This is the class of Cashier who is in charge of checking out goods from inventory.
 */
public class Cashier {
  private static int orderNumber = 1;

  /**
   * Method to take out a number amount of goods from One certain good, which store as upc in
   * inventory.
   *
   * @param upc String the exact good
   * @param num Integer the quantity scanned out
   */
  public void scanOut(String upc, Integer num) {
    String table = DataBaseViewer.getInventoryName(upc);
    int oldQuantity = DataBaseViewer.getInt(upc, table, "quantity");
    int newQuantity = oldQuantity - num;
    // set new Quantity to the goods.
    if(num > 0){
      if (newQuantity >= 0) {
        double currentPrice = DataBaseViewer.getDouble(upc,table,"price");
        DataBaseOperator.insertPriceHistory(upc,currentPrice);
        DataBaseOperator.updateQuantity(upc, newQuantity);
        // set data on saleHistory table in dataBase.
        DataBaseOperator.insertSaleHistory(upc, num);
        // Call autoOrder if the quantity is lower than the threshold.
        int threshold = DataBaseViewer.getInt(upc, table, "threshold");
        boolean hasOrder = DataBaseViewer.loopColumnForString("upc", upc, "Orders");
        if (hasOrder) {
          boolean Pending = DataBaseViewer.loopColumnForString("isPending","true", "Orders");
          if (!Pending) {
            if (newQuantity < threshold) {
              autoOrder(upc);
              JOptionPane.showMessageDialog(null,
                      "Goods Out Of Stork and An Order has been sent",
                      "Order", JOptionPane.PLAIN_MESSAGE);
            }
          }
        } else {
          if (newQuantity < threshold) {
            autoOrder(upc);
            JOptionPane.showMessageDialog(null,
                    "Goods Out Of Stork and An Order has been sent",
                    "Order", JOptionPane.PLAIN_MESSAGE);
          }
        }
      } else {
        JOptionPane.showMessageDialog(null,
                "Goods Out Of Stork!", "Warning",  JOptionPane.WARNING_MESSAGE);
      }} else {
      JOptionPane.showMessageDialog(null,
              "<html><b style='color:red'>InValid input Quantity!", "Warning",  JOptionPane.WARNING_MESSAGE);
    }
  }
  /**
   * Return an ArrayList which consists of all all onSale time of one good from inventory.
   */
  public void checkOnSaleTime() {
    DataBaseShowTable dataBaseShowTable = new DataBaseShowTable("OnSaleTime");
  }

  /**
   * Construct an Order and put the Order to Orders inventory.
   *
   * @param upc String
   */
  private void autoOrder(String upc){
    String table = DataBaseViewer.getInventoryName(upc);
    String name = DataBaseViewer.getString(upc,table,"name");
    int threshold = DataBaseViewer.getInt(upc,table,"threshold");
    Order order = new Order(orderNumber,upc,name,"true",threshold);
    orderNumber++;
    DataBaseOperator.insertOrder(order);
  }
}