package local.netts3s.utils.db;

import java.sql.*;

public class JDBCConnection {
    private static Connection connection;

    static {
        try {
            Class.forName( DBConstants.DRIVER_NAME );
            System.out.println("SQL driver loaded!");
        }
        catch ( ClassNotFoundException e ) {
            System.out.println( "Driver class not found" );
        }
    }

    public static Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(DBConstants.URL, DBConstants.USERNAME, DBConstants.PASSWORD);
        return connection;
    }

    public static void closeConnection( Connection connection ) throws SQLException {
        if (connection != null ) {
            connection.close();
        }
    }

    public static void closePreparedStatement( PreparedStatement statement ) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }
}
