package core;

import app.Reservation;
import app.User;

import java.util.List;

public abstract class App {
    public abstract Reservation makeReservation(User user,  Reservation reservation);
    public abstract List<Reservation> getReservations(User user);
}
