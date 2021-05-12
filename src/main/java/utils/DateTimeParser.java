package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeParser {
    public static final String datePattern = "dd.MM.yyyy";
    public static final String timePattern = "HH:mm";

    private DateTimeParser() {}

    public static LocalDate parseDate (String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern));
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    public static LocalTime parseTime (String time) {
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern(timePattern));
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    public static String dateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(datePattern));
    }

    public static String timeToString(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern(timePattern));
    }
}
