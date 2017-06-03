package am.aca.wftartproject.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {

    private Properties properties = new PropertyHelper().getProperties();

    private static ConnectionPool instance;

    private ComboPooledDataSource dataSource;

    private ConnectionPool() throws PropertyVetoException {
        dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(properties.getProperty("c3p0.driver"));
        dataSource.setJdbcUrl(properties.getProperty("c3p0.jdbcURL"));
        dataSource.setUser(properties.getProperty("c3p0.user"));
        dataSource.setPassword(properties.getProperty("c3p0.password"));


        //set other properties

    }

    public static ConnectionPool getInstance() throws PropertyVetoException {
        if (instance == null){
            instance = new ConnectionPool();
            return instance;
        }
        else {
            return instance;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }



    /*private static PropertyHelper propertyHelper = new PropertyHelper();
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
    }*/


}
