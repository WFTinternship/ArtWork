package am.aca.wftartproject.exception;

/**
 * Created by ASUS on 03-Jun-17
 */
public class DAOFailException extends RuntimeException {

    public DAOFailException(Exception e){
        super("DB error: ",e);
    }

    public DAOFailException(String message){
        super(message);
    }

    public DAOFailException(String message, Exception e){
        super(message,e);
    }

}
