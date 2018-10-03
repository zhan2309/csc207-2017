package ProjectPhase1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class is used when user is logged in as a type of receiver.
 */
public class Receiver {

    // Connect to DataBase.
    private static Connection connection = SqliteConnection.dbConnector();
    /**
     * This method is used to scan in good.
     * @param goods good
     */
    public void scanIn(Goods goods, String table){
        DataBaseOperator.createTable(table);
        if (!DataBaseViewer.loopColumnForString("upc",goods.getUpc(),"InventoryName")) {
            // add one data on the table of InventoryName.
            DataBaseOperator.insertInventoryName(goods.getUpc(), table);
        }

        if (DataBaseViewer.loopColumnForString("upc", goods.getUpc(), table)){
            int newQuantity = goods.getQuantity() + DataBaseViewer.getInt(goods.getUpc(),table,"quantity");
            DataBaseOperator.updateQuantity(goods.getUpc(),newQuantity);
        } else {
            DataBaseOperator.insertGood(table,goods);
        }
    }

    /**
     * This method is used to view location of good
     * @param upc given upc of good
     * @return location of good
     */
    public String viewLocation(String upc) {
        String inventoryName = DataBaseViewer.getInventoryName(upc);
        return "Store:" + DataBaseViewer.getInventoryName(upc) +" : "+ DataBaseViewer.getString(upc, inventoryName,"location");
    }

    /**
     * This method is used to view price history of good.
     */
    public void viewPriceHistory() {
        DataBaseShowTable dataBaseShowTable = new DataBaseShowTable("PriceHistory");
    }

    /**
     * This method is used to get cost of good
     * @param upc given upc
     * @return cost of good
     */
    public double getCost(String upc) {
        String inventoryName = DataBaseViewer.getInventoryName(upc);
        return DataBaseViewer.getDouble(upc, inventoryName, "cost");
    }

    /**
     * This method is used to get price of good
     * @param upc given upc
     * @return price of good
     */
    public double getPrice(String upc) {
        String inventoryName = DataBaseViewer.getInventoryName(upc);
        return DataBaseViewer.getDouble(upc, inventoryName, "price");
    }

    /**
     * Get threshold of a good.
     * @param upc String
     * @return int
     */
    public int getThreshold(String upc) {
        String inventoryName = DataBaseViewer.getInventoryName(upc);
        return DataBaseViewer.getInt(upc, inventoryName, "threshold");
    }

    /**
     * Use order number to update storage.
     * @param orderNumber int
     */
    public void dealWithOrder(int orderNumber) {
        String sql = "UPDATE Orders SET isPending = ? where rowid = " + orderNumber;

        if (connection != null) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                if (DataBaseViewer.loopRowIdToGetString("isPending", orderNumber, "Orders").equals("true")) {
                    // refill the goods quantity to the Old goods
                    String upc = DataBaseViewer.loopRowIdToGetString("upc", orderNumber, "Orders");
                    int quantity = DataBaseViewer.getInt(upc, "Orders", "quantity");
                    String inventoryName = DataBaseViewer.getInventoryName(upc);
                    int oldQuantity = DataBaseViewer.getInt(upc, inventoryName, "quantity");
                    DataBaseOperator.updateQuantity(upc, quantity + oldQuantity);
                    // set the corresponding param
                    preparedStatement.setString(1, "false");

                    // update
                    preparedStatement.executeUpdate();
                }
            } catch(SQLException e){
                Helpers.showInVaildMessage();

            }
        }
    }

}