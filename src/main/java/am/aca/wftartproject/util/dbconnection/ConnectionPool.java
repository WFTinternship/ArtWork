package am.aca.wftartproject.util.dbconnection;

import am.aca.wftartproject.util.PropertyHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ASUS on 27-May-17
 */
public class ConnectionPool implements DatabaseConnection {

    private static ComboPooledDataSource cpds = new ComboPooledDataSource();
    private static PropertyHelper propertyHelper = new PropertyHelper();

    @Override
    public Connection getProductionDBConnection() throws SQLException, ClassNotFoundException {
        return getDBConnection("jdbcUrl");
    }

    @Override
    public Connection getTestDBConnection() throws SQLException, ClassNotFoundException {
        return getDBConnection("jdbcUrlTest");
    }


    public Connection getDBConnection(String dbUrl) throws SQLException {

        cpds.setJdbcUrl(propertyHelper.getProperties().getProperty(dbUrl));
        cpds.setUser(propertyHelper.getProperties().getProperty("jdbcUserName"));
        cpds.setPassword(propertyHelper.getProperties().getProperty("jdbcPassword"));

        // Optional Settings
        cpds.setInitialPoolSize(5);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(100);

        return cpds.getConnection();
    }


//    private BasicDataSource connectionPool = null;
//
//    public void setConnectionAttributes() {
//        try {
//            URI dbURI = new URI(System.getenv("DATABASE_URL"));
//            String dbUrl = "jdbc:mysql://" + dbURI.getHost() + ":" + dbURI.getPort() + dbURI.getPath();
//            connectionPool = new BasicDataSource();
//
//            if (dbURI.getUserInfo() != null) {
//                connectionPool.setUsername(dbURI.getUserInfo().split("=")[1]);
//                connectionPool.setPassword(dbURI.getUserInfo().split("=")[3]);
//            }
//            connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
//            connectionPool.setUrl(dbUrl);
//            connectionPool.setInitialSize(3);
//
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//    }

//    public enum DBType {
//        REAL,
//        TEST
//    }
//
//    public static Connection getDataSource(DBType dbType) throws PropertyVetoException, SQLException {
//
//        PropertyHelper propertyHelper = new PropertyHelper();
//        ComboPooledDataSource cpds = new ComboPooledDataSource();
//        cpds.setJdbcUrl(propertyHelper.getProperties().getProperty(dbType.equals(DBType.REAL) ? "jdbcUrl" : "jdbcUrlTest"));
//        cpds.setUser(propertyHelper.getProperties().getProperty("jdbcUserName"));
//        cpds.setPassword(propertyHelper.getProperties().getProperty("jdbcPassword"));
//
//        // Optional Settings
//        cpds.setInitialPoolSize(5);
//        cpds.setMinPoolSize(5);
//        cpds.setAcquireIncrement(5);
//        cpds.setMaxPoolSize(20);
//        cpds.setMaxStatements(100);
//
//        return cpds.getConnection();
//    }


}
