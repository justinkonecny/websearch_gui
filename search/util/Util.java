package search.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Util {

    /**
     * Returns the given String with all words capitalized.
     *
     * @param str the String to capitalize
     * @return the capitalized String
     */
    public static String getCapitalized(String str) {
        if (str != null && !str.equals("")) {
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
            for (int i = 1; i < str.length(); i++) {
                if (str.charAt(i) == ' ') {
                    str = str.substring(0, i + 1) + str.substring(i + 1, i + 2).toUpperCase() + str.substring(i + 2);
                }
            }
        }
        return str;
    }

    /**
     * Calculates the time difference (in days) between now and the given date.
     *
     * @param year the date's year
     * @param month the date's month
     * @param day the date's day
     * @return the difference in time (in days)
     */
    public static int getTimeDelta(int year, int month, int day) {
        LocalDate then = LocalDate.of(year, month, day);
        LocalDate now = LocalDateTime.now().toLocalDate();
        Period period = Period.between(then, now);
        return period.getDays() + (period.getMonths() * 30);
    }
}