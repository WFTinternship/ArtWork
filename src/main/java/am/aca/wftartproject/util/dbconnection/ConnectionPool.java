package am.aca.wftartproject.util.dbconnection;

import am.aca.wftartproject.util.PropertyHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by ASUS on 27-May-17
 */
public class ConnectionPool implements DatabaseConnection {

    private static ComboPooledDataSource cpds = new ComboPooledDataSource();
    private static PropertyHelper propertyHelper = new PropertyHelper();

    @Override
    public DataSource getProductionDBConnection() throws SQLException, ClassNotFoundException {
        return getDBConnection("jdbcUrl");
    }

    @Override
    public DataSource getTestDBConnection() throws SQLException, ClassNotFoundException {
        return getDBConnection("jdbcUrlTest");
    }


    private DataSource getDBConnection(String dbUrl) throws SQLException {
        cpds.setJdbcUrl(propertyHelper.getProperties().getProperty(dbUrl));
        cpds.setUser(propertyHelper.getProperties().getProperty("jdbcUserName"));
        cpds.setPassword(propertyHelper.getProperties().getProperty("jdbcPassword"));

        // Optional Settings
        cpds.setInitialPoolSize(5);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(100);

        return cpds;
    }
}
