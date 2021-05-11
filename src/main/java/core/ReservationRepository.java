package core;

import app.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> getAll(String email);
    Reservation add(Reservation reservation);
    void getData();
}
