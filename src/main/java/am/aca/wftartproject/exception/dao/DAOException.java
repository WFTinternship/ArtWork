package am.aca.wftartproject.exception.dao;

/**
 * Created by ASUS on 03-Jun-17
 */
public class DAOException extends RuntimeException {

    public DAOException(Exception e) {
        super("DAO layer error: ", e);
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Exception e) {
        super(message, e);
    }
}
