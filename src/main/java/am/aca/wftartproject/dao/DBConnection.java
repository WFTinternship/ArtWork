package am.aca.wftartproject.dao;

import am.aca.wftartproject.util.PropertyHelper;
import am.aca.wftartproject.util.PropertyLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by ASUS on 27-May-17.
 */
public class DBConnection {

    private Properties dbProps = PropertyLoader.getProperties("database-config.properties");
    private Connection conn;

    public enum DBType {
        REAL,
        TEST
    }

    public Connection getDBConnection(DBType dbType) throws SQLException, ClassNotFoundException {
        Class.forName(dbProps.getProperty("jdbcDriver"));
        Connection conn = DriverManager.getConnection(
                dbProps.getProperty(dbType.equals(DBType.REAL) ? "jdbcUrl" : "jdbcUrlTest"),
                dbProps.getProperty("jdbcUserName"),
                dbProps.getProperty("jdbcPassword")
        );

        return conn;
    }
}
