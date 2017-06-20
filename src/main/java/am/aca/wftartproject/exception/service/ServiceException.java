package am.aca.wftartproject.exception.service;

/**
 * @author surik
 */
public class ServiceException extends RuntimeException {

    public ServiceException(Exception e) {
        super("Service layer error: ", e);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}
