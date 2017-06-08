package am.aca.wftartproject.util.dbconnection;

/**
 * Created by ASUS on 04-Jun-17
 */
public class ConnectionFactory {

    public DatabaseConnection getConnection(ConnectionModel connModel) {

        if (connModel == null) {
            return null;
        }

        if (connModel.equals(ConnectionModel.POOL)) {
            return new ConnectionPool();
        }
        return null;
    }

}
