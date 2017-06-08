package am.aca.wftartproject.util.dbconnection;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by ASUS on 04-Jun-17
 */
public interface DatabaseConnection {

    DataSource getTestDBConnection() throws SQLException, ClassNotFoundException;

    DataSource getProductionDBConnection() throws SQLException, ClassNotFoundException;

}
