package core;

import com.fasterxml.jackson.annotation.JsonProperty;
import utils.DateTimeParser;
import java.time.format.DateTimeParseException;

public class Reservation implements Comparable<Reservation> {
    private final String date;
    private final String time;
    private final User user;

    /**
     * @param date reservation date
     * @param time reservation time
     * @param user user
     */
    public Reservation(
            @JsonProperty("date") String date,
            @JsonProperty("time") String time,
            @JsonProperty("user") User user
    ) {
        this.date = date;
        this.time = time;
        this.user = user;
    }

    /**
     * @return reservation date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return reservation time
     */
    public String getTime() {
        return time;
    }

    /**
     * @return reservation owner
     */
    public User getUser() {
        return user;
    }

    /**
     * @return reservation information
     */
    @Override
    public String toString() {
        return String.format(
                "RESERVATION:\nDate: %s\nTime: %s\n%s\n",
                getDate(),
                getTime(),
                getUser()
        );
    }

    @Override
    public int compareTo(Reservation res) {
        try {
            return DateTimeParser.parseDate(date).compareTo(DateTimeParser.parseDate(res.getDate())) + DateTimeParser.parseTime(time).compareTo(DateTimeParser.parseTime(res.getTime()));
        } catch (DateTimeParseException ex) {
            throw new DBIncorrectDateTimeFormat("Error accreted while parsing date and time strings from data store to LocalDate and LocalTime. " + ex.getMessage());
        }
    }
}
