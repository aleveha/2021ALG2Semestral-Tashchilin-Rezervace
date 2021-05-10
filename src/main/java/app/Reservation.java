package app;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
     LocalDate date;
     LocalTime time;
     User user;

    /**
     *
     * @param date reservation date
     * @param time reservation time
     * @param user user
     */
    public Reservation(LocalDate date, LocalTime time, User user) {
        this.date = date;
        this.time = time;
        this.user = user;
    }

    /**
     *
     * @return reservation date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     *
     * @return reservation time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     *
     * @return reservation owner
     */
    public User getUser() {
        return user;
    }

    /**
     *
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
