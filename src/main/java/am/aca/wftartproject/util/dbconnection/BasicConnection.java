package am.aca.wftartproject.util.dbconnection;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by ASUS on 27-May-17
 */
public class BasicConnection implements DatabaseConnection {
    @Override
    public DataSource getTestDBConnection() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public DataSource getProductionDBConnection() throws SQLException, ClassNotFoundException {
        return null;
    }
//
//    private static PropertyHelper propertyHelper = new PropertyHelper();
//
//    @Override
//    public Connection getProductionDBConnection() throws SQLException, ClassNotFoundException {
//        return getDBConnection("jdbcUrl");
//    }
//
//    @Override
//    public Connection getTestDBConnection() throws SQLException, ClassNotFoundException {
//        return getDBConnection("jdbcUrlTest");
//    }
//
//
//    public Connection getDBConnection(String dbUrl) throws ClassNotFoundException, SQLException {
//        Class.forName(propertyHelper.getProperties().getProperty("jdbcDriver"));
//        Connection conn = DriverManager.getConnection(
//                propertyHelper.getProperties().getProperty(dbUrl),
//                propertyHelper.getProperties().getProperty("jdbcUserName"),
//                propertyHelper.getProperties().getProperty("jdbcPassword")
//        );
//        return conn;
//    }
//
}
