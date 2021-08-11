package local.netts3s;

import local.netts3s.entity.Track;
import local.netts3s.utils.XMLParser;
import local.netts3s.utils.db.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static final String INSERT_SQL_QUERY = "INSERT IGNORE INTO tracklist(name) VALUES (?)";

    public static void main(String[] args) throws SQLException {
        long start = System.currentTimeMillis();
        List<Track> tracks = new XMLParser().parseFromFile("tracklist.xml");
        App app = new App();
        app.insertTrack(tracks);
        System.out.println("Done.\nTime: " + (System.currentTimeMillis() - start));
    }

    private void insertTrack(List<Track> tracks) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = JDBCConnection.getConnection();
            if (connection == null) {
                System.out.println("Error getting the connection.");
                return;
            }
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(INSERT_SQL_QUERY);

            int i = 0;
            for(Track track : tracks) {
                statement.setString(1, track.getName());
                statement.addBatch();
                if (++i % 250 == 0) {
                    statement.executeBatch();
                }
            }
            statement.executeBatch();
            statement.execute();
            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            JDBCConnection.closePreparedStatement(statement);
            JDBCConnection.closeConnection(connection);
        }
    }
}