package am.aca.wftartproject.exception;

/**
 * @author surik
 */
public class DAOException extends RuntimeException{
    public DAOException(Exception e){
        super("DB error: ",e);
    }

    public DAOException(String message){
        super(message);
    }

    public DAOException(String message, Exception e){
        super(message,e);
    }
}
