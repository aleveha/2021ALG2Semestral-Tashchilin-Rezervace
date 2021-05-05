package app;

import core.App;

import java.util.ArrayList;
import java.util.List;

public class DoMeApp extends App {
    List<Reservation> res = new ArrayList<>();

    @Override
    public boolean makeReservation(User user, Reservation reservation) {
        if (user != null) {
            this.res.add(reservation);
            return true;
        }
        return false;
    }

    @Override
    public List<Reservation> getReservations(User user) {
        return res;
    }
}
