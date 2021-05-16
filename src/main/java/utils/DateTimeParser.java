package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeParser {
    public static final String datePattern = "dd.MM.yyyy";
    public static final String timePattern = "HH:mm";

    private DateTimeParser() {}

    /**
     * Parsing string to be parsed and checked
     * @param date date as string
     * @return LocalDate if input string is a real date according to special format
     */
    public static LocalDate parseDate (String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern));
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    /**
     * Parsing string to be parsed and checked
     * @param time time as string
     * @return LocalTime if input string is a real time according to special format
     */
    public static LocalTime parseTime (String time) {
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern(timePattern));
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    /**
     * Parsing LocalDate in special format to string
     * @param date date to be parsed
     * @return parsed date as string
     */
    public static String dateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(datePattern));
    }

    /**
     * Parsing LocalTime in special format to string
     * @param time time to be parsed
     * @return parsed time as string
     */
    public static String timeToString(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern(timePattern));
    }
}
