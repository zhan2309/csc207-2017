package ProjectPhase1;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class contain all needed helper function.
 */
public class Helpers {

  /**
   * Helper to get system time.
   * @return String
   */
  public static String getSystemTime(){
    Date date=new Date();
    DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format.format(date);
  }

  /**
   * Check if the upc is valid.
   * @param upc String
   * @return Boolean
   */
  public static boolean isValidUPc(String upc){
    try {
    String pattern = "^\\d{12}$";
    Pattern r = Pattern.compile(pattern);
    Matcher m = r.matcher(upc);
    if (m.find()){
      return true;
    } else {
      return false;
    }} catch (Exception e1){
      Helpers.showInvalidUpc();
      return false;
    }
  }

  /**
   * Check if upc exist.
   * @param upc String
   * @return Boolean
   */
  public static boolean isUpcExist(String upc){
    return DataBaseViewer.loopColumnForString("upc",upc,"InventoryName");
  }

  /**
   * print a message that the upc is invalid, try again.
   */
  public static void showInvalidUpc(){
    JOptionPane.showMessageDialog(null,
            "<html><b style='color:red'>Invalid UPC could you please input a formal upc",
            "Invalid UPC", JOptionPane.WARNING_MESSAGE);
  }

  /**
   * Warning the upc is invalid.
   */
  public static void showInVaildMessage(){
    JOptionPane.showMessageDialog(null,
            "<html><b style='color:red'>INVAILD INPUT INFORMATION",
            "Invalid INPUT", JOptionPane.WARNING_MESSAGE);
  }

  /**
   * Check if the entered date is valid.
   * @param date String
   * @return Boolean
   */
  public static boolean isValidDate(String date) {
      String pattern = "^\\d{4}-\\d{2}-\\d{2}$";
      Pattern r = Pattern.compile(pattern);
      Matcher m = r.matcher(date);
      if (m.find()) {
        return true;
      } else {
        Helpers.showInvalidDateDialog();
        return false;
      }

  }

  /**
   * Print invalid date message.
   */
  public static void showInvalidDateDialog(){
    JOptionPane.showMessageDialog(null,
            "<html><b style='color:red'>Invalid input Date",
            "Invalid Date", JOptionPane.WARNING_MESSAGE);
  }

  /**
   * Print date not exist massage.
   */
  public static void showDateDoesNotExistDialog(){
    JOptionPane.showMessageDialog(null,
            "Date Does Not Exist",
            "Invalid Date", JOptionPane.WARNING_MESSAGE);
  }
}