package am.aca.wftartproject.exception.service;

/**
 * Created by ASUS on 13-Jun-17
 */
public class InvalidEntryException extends RuntimeException {

    public InvalidEntryException(Exception e) {
        super("Service layer error: ", e);
    }

    public InvalidEntryException(String message) {
        super(message);
    }

    public InvalidEntryException(String message, Exception e) {
        super(message, e);
    }

}
