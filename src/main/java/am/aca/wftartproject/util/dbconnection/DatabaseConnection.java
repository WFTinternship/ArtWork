package am.aca.wftartproject.util.dbconnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ASUS on 04-Jun-17
 */
public interface DatabaseConnection {

    Connection getTestDBConnection() throws SQLException, ClassNotFoundException;

    Connection getProductionDBConnection() throws SQLException, ClassNotFoundException;

}
