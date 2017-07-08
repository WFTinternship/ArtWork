package am.aca.wftartproject.util;

import java.time.LocalDateTime;

/**
 * @author surik
 */
public class DateHelper {
    public static boolean dateComparison(LocalDateTime date1, LocalDateTime date2) {
        return date1.equals(date2);
    }
}
