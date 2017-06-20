package am.aca.wftartproject.exception.service;

/**
 * Created by ASUS on 18-Jun-17
 */
public class DuplicateEntryException extends RuntimeException {

    public DuplicateEntryException(Exception e) {
        super("Service layer error: ", e);
    }

    public DuplicateEntryException(String message) {
        super(message);
    }

    public DuplicateEntryException(String message, Exception e) {
        super(message, e);
    }
}
