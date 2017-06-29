package am.aca.wftartproject.exception.dao;

/**
 * Created by ASUS on 24-Jun-17
 */
public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException(Exception e) {
        super("DAO layer error: ", e);
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }

    public NotEnoughMoneyException(String message, Exception e) {
        super(message, e);
    }
}