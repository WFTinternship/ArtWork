package am.aca.wftartproject.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by ASUS on 27-May-17.
 */
public class ConnectionPool {

    private BasicDataSource connectionPool = null;


    public void setConnectionAttributes(){
        try {
            URI dbURI = new URI(System.getenv("DATABASE_URL"));
            String dbUrl = "jdbc:mysql://"+dbURI.getHost() + ":" +dbURI.getPort() + dbURI.getPath();
            connectionPool = new BasicDataSource();

            if(dbURI.getUserInfo() != null){
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
}
