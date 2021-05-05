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

    @Override
    public String toString() {
        return "Reservation{" +
                "date=" + date +
                ", time=" + time +
                ", user=" + user.getFirstName() +
                '}';
    }
}
