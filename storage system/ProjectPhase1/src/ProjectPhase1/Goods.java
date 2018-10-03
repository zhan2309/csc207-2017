package ProjectPhase1;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

/**
 * Class Goods which could be put in or take out from the inventory.
 */
public class Goods implements Serializable {

  /**
   * The Serialized String on goods.
   */
  private String upc;
  /**
   * The name of the goods.
   */
  private String name;
  /**
   * The quantity of the goods.
   */

  private int quantity;
  /**
   * The price of the goods.
   */
  private Double price;

  /**
   * The location of the goods.
   */
  private String location;
  /**
   * The cost of the goods.
   */
  private Double cost;
  /**
   * The threshold of the goods.
   */
  private int threshold;

  /**
   * The Constructor of the Goods.
   *
   * @param upc String unique identifier for the goods.
   * @param name String the Name of the goods.
   * @param quantity int the quantity of the goods.
   * @param price Double the price of the goods.
   * @param location String the location of the goods.
   * @param cost Double the cost of the goods.
   * @param threshold int the threshold for the goods lower the threshold system will automatically
   *     create orders to Orders.
   */
  public Goods(String upc, String name, int quantity, Double price, String location,
      Double cost, int threshold) {
    this.upc = upc;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    this.location = location;
    this.cost = cost;
    this.threshold = threshold;
  }

  /**
   * Return Upc of this Goods.
   *
   * @return String
   */
  public String getUpc() {
    return upc;
  }

  /**
   * Modify the value of Upc.
   *
   * @param upc String
   */
  public void setUpc(String upc) {
    this.upc = upc;
  }

  /**
   * Return the location of this Goods.
   *
   * @return String
   */
  public String getLocation() {
    return location;
  }

  /**
   * Modify the location of the Goods.
   *
   * @param location String
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Return the name of this Goods.
   *
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Modify the name of the Goods.
   *
   * @param name String
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Return the Quantity of the Goods.
   *
   * @return int
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Modify the value of the quantity of the Goods.
   *
   * @param quantity int
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Return whether the Goods is on sale or not.
   *
   * @return boolean True means Goods is on sale and False otherwise.
   */

  public Double getPrice() {
    return price;
  }

  /**
   * Modify the value of the price of the Goods.
   *
   * @param price Double
   */
  public void setPrice(Double price) {
    this.price = price;
  }

  /**
   * Return the Threshold.
   *
   * @return int
   */
  public int getThreshold() {
    return threshold;
  }

  /**
   * @return Return Cost.
   */
  public Double getCost() {
    return cost;
  }

}
