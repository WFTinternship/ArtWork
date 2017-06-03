package am.aca.wftartproject.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by ASUS on 27-May-17.
 */
public class DBConnection {

    private Properties dbProperties = new PropertyHelper().getProperties();

    public enum DBType {
        REAL,
        TEST
    }

    public Connection getDBConnection(DBType dbType) throws SQLException, ClassNotFoundException {

        Class.forName(dbProperties.getProperty("jdbcDriver"));

        return DriverManager.getConnection(
                dbProperties.getProperty(dbType.equals(DBType.REAL) ? "jdbcUrl" : "jdbcUrlTest"),
                dbProperties.getProperty("jdbcUserName"),
                dbProperties.getProperty("jdbcPassword")
        );

    }
}