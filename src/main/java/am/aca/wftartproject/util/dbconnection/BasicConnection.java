package am.aca.wftartproject.util.dbconnection;

import am.aca.wftartproject.util.PropertyHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ASUS on 27-May-17
 */
public class BasicConnection {

    private static PropertyHelper propertyHelper = new PropertyHelper();

    public Connection getProductionDBConnection() throws SQLException, ClassNotFoundException {
        return getDBConnection("jdbcUrl");
    }

    public Connection getTestDBConnection() throws SQLException, ClassNotFoundException {
        return getDBConnection("jdbcUrlTest");
    }


    private Connection getDBConnection(String dbUrl) throws ClassNotFoundException, SQLException {
        Class.forName(propertyHelper.getProperties().getProperty("jdbcDriver"));
        Connection conn = DriverManager.getConnection(
                propertyHelper.getProperties().getProperty(dbUrl),
                propertyHelper.getProperties().getProperty("jdbcUserName"),
                propertyHelper.getProperties().getProperty("jdbcPassword")
        );
        return conn;
    }
}
