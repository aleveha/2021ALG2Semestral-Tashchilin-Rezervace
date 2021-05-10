package app;

import core.App;

import java.util.ArrayList;
import java.util.List;

public class DoMeApp extends App {
    List<Reservation> reservations = new ArrayList<>();

    @Override
    public Reservation makeReservation(User user, Reservation reservation) {
        if (user != null) {
            this.reservations.add(reservation);
            return reservation;
        }
        return null;
    }

    @Override
    public List<Reservation> getReservations(User user) {
        return reservations;
    }
}
