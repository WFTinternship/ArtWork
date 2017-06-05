package am.aca.wftartproject.util.dbconnection;

import am.aca.wftartproject.util.PropertyHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ASUS on 27-May-17
 */
public class SingletonConnection implements DatabaseConnection {

    private static volatile SingletonConnection connectionInstance;

    private SingletonConnection() {
    }

    public static SingletonConnection getInstance() {
        if (connectionInstance == null) {
            synchronized (SingletonConnection.class) {
                if (connectionInstance == null) {
                    connectionInstance = new SingletonConnection();
                }
            }
        }
        return connectionInstance;
    }

    @Override
    public Connection getProductionDBConnection() throws SQLException, ClassNotFoundException {
        return getDBConnection("jdbcUrl");
    }

    @Override
    public Connection getTestDBConnection() throws SQLException, ClassNotFoundException {
        return getDBConnection("jdbcUrlTest");
    }


    public Connection getDBConnection(String dbUrl) throws ClassNotFoundException, SQLException {
        PropertyHelper propertyHelper = new PropertyHelper();
        Class.forName(propertyHelper.getProperties().getProperty("jdbcDriver"));
        Connection conn = DriverManager.getConnection(
                propertyHelper.getProperties().getProperty(dbUrl),
                propertyHelper.getProperties().getProperty("jdbcUserName"),
                propertyHelper.getProperties().getProperty("jdbcPassword")
        );
        return conn;
    }


//    public enum DBType {
//        REAL,
//        TEST
//    }


}
