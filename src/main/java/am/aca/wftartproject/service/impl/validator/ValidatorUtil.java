package am.aca.wftartproject.service.impl.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author surik
 */
public class ValidatorUtil {


    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmailAddressForm(String email) {
        if (isEmptyString(email)) {
            return false;
        } else {
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }
    public static boolean isEmptyString(String string) {
        return (string == null || string.isEmpty());
    }

}
