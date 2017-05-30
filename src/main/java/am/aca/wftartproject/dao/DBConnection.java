package am.aca.wftartproject.dao;

import am.aca.wftartproject.util.PropertyHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ASUS on 27-May-17.
 */
public class DBConnection {

    private PropertyHelper propertyHelper = new PropertyHelper();
    private Connection conn;

    public enum DBType {
        REAL,
        TEST
    }

    public Connection getDBConnection(DBType dbType) throws SQLException, ClassNotFoundException {
        Class.forName(propertyHelper.getProperties().getProperty("jdbcDriver"));
        Connection conn = DriverManager.getConnection(
                propertyHelper.getProperties().getProperty(dbType.equals(DBType.REAL) ? "jdbcUrl" : "jdbcUrlTest"),
                propertyHelper.getProperties().getProperty("jdbcUserName"),
                propertyHelper.getProperties().getProperty("jdbcPassword")
        );
        return conn;
    }
}
