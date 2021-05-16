package core;

import app.Reservation;
import app.User;

import java.util.List;

public abstract class App {
    /**
     * Making new reservation
     * @param reservation reservation
     * @return new reservation
     */
    public abstract Reservation makeReservation(Reservation reservation);

    /**
     * Getting all user's reservations
     * @param user user
     * @return list of reservations
     */
    public abstract List<Reservation> getReservations(User user);
}
