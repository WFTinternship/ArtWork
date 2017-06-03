package am.aca.wftartproject.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import java.beans.PropertyVetoException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by ASUS on 27-May-17
 */
public class ConnectionPool {

    private static PropertyHelper propertyHelper = new PropertyHelper();
    private BasicDataSource connectionPool = null;

    public void setConnectionAttributes() {
        try {
            URI dbURI = new URI(System.getenv("DATABASE_URL"));
            String dbUrl = "jdbc:mysql://" + dbURI.getHost() + ":" + dbURI.getPort() + dbURI.getPath();
            connectionPool = new BasicDataSource();

            if (dbURI.getUserInfo() != null) {
                connectionPool.setUsername(dbURI.getUserInfo().split("=")[1]);
                connectionPool.setPassword(dbURI.getUserInfo().split("=")[3]);
            }
            connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
            connectionPool.setUrl(dbUrl);
            connectionPool.setInitialSize(3);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }


    public static ComboPooledDataSource getDataSource() throws PropertyVetoException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl(propertyHelper.getProperties().getProperty("jdbcUrl"));
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
