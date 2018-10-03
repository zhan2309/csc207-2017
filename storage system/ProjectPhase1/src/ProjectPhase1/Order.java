package ProjectPhase1;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by drixs on 2017/7/15.
 */
public class Order implements Serializable {

  /**
   * The upc of the Order.
   */
  private String upc;
  /**
   * The name of the Order
   */
  private String name;
  /**
   * The status of pending for Order.
   */
  private String isPending;
  /**
   * The quantity that will fill
   */
  private int quantity;
  /**
   * This field indicates order number.
   */
  private int orderNumber;

  /**
   * This method is used to order goods.
   * @param upc upc of good
   * @param name name of good
   * @param isPending whether good is pending or not
   * @param quantity amount of this good
   */
  public Order(int orderNumber, String upc, String name, String isPending, int quantity) {
    this.upc = upc;
    this.name = name;
    this.isPending = isPending;
    this.quantity = quantity;
    this.orderNumber = orderNumber;
  }
  public int getOrderNumber(){
    return orderNumber;
  }
  /**
   * This method is used to get UPC of good.
   * @return upc of good
   */
  public String getUpc() {
    return upc;
  }

  /**
   * This method is used to set upc of good.
   * @param upc given upc
   */
  public void setUpc(String upc) {
    this.upc = upc;
  }

  /**
   * This method is used to get name of good.
   * @return return name of good
   */
  public String getName() {
    return name;
  }

  /**
   * This method is used to set name of good.
   * @param name given name of good.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * This method is used to return status of good.
   * @return
   */
  public String getIsPending() {
    return isPending;
  }


  /**
   * This method is used to get quantity of good.
   * @return quantity of good
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * This method is used to set quantity of good.
   * @param quantity given quantity of good.
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

}
