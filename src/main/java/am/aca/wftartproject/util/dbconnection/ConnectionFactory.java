package am.aca.wftartproject.util.dbconnection;

/**
 * Created by ASUS on 04-Jun-17
 */
public class ConnectionFactory {

    public DatabaseConnection getConnection(ConnectionModel connModel) {

        if (connModel == null) {
            return null;
        }

        if (connModel.equals(ConnectionModel.SINGLETON)) {
            return SingletonConnection.getInstance();
        } else if (connModel.equals(ConnectionModel.CONNECTIONPOOL)) {
            return ConnectionPool.getInstance();
        }
        return null;
    }

}
