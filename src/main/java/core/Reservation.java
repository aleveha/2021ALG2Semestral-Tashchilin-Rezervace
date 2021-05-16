package core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Reservation {
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
}
