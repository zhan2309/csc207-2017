package ProjectPhase1;

import java.awt.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;

/**
 * Class for displaying Table.
 */
public class DataBaseShowTable extends JFrame{

    // Connect to DataBase.
    private static Connection connection = SqliteConnection.dbConnector();

    /**
     * Print a table in gui.
     * @param tableName String
     */
    public DataBaseShowTable(String tableName) {
        try {
            Statement connectionStatement = connection.createStatement();
            ResultSet rs = connectionStatement.executeQuery("select * from "+ tableName);

            // Here creates and displays the table
            JTable table = new JTable(createTable(rs));
            JScrollPane jScrollPane = new JScrollPane(table);
            jScrollPane.setPreferredSize(new Dimension(850,700));

            JOptionPane.showMessageDialog(null,
                    jScrollPane,tableName,JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e1) {
            Helpers.showInVaildMessage();
        }
    }

    /**
     * Create a table.
     * @param rs ResultSet
     * @return DefaultTableModel
     */
    private static DefaultTableModel createTable(ResultSet rs) {
        try {
            int columnNumber;
            Vector<String> columnNames;
            ResultSetMetaData metaData = rs.getMetaData();

            // all names of columns
            columnNames = new Vector<>();
            columnNumber = metaData.getColumnCount();
            for (int column = 1; column <= columnNumber; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            // all data from the table of the DataBase
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<>();
                for (int columnIndex = 1; columnIndex <= columnNumber; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            return new DefaultTableModel(data, columnNames);

        } catch (Exception e1) {
            Helpers.showInVaildMessage();
            return null;
        }
    }
}
