package ProjectPhase1;

import java.sql.*;

/**
 * Construct a connection to a database.
 */
public class SqliteConnection {

    /**
     * Connect to database.
     * @return Connection
     */
    public static Connection dbConnector(){

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:test.sqlite");
            return connection;

        } catch (Exception e){
            System.out.println("There is no JDBC");
            return null;
        }

    }

}
