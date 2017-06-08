package am.aca.wftartproject.service.impl.validator;

/**
 * @author surik
 */
public class ValidatorUtil {
    public static boolean isEmptyString(String string){
        return (string == null || string.isEmpty());
    }
}
