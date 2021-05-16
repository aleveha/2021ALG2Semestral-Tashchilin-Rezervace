package core;

import app.Reservation;

import java.util.List;

public interface ReservationRepository {
    /**
     * Getting all user's reservation
     * @param email user's email
     * @return list of reservations
     */
    List<Reservation> getAll(String email);

    /**
     * Adding a new reservation
     * @param reservation new reservation
     * @return new reservation
     */
    Reservation add(Reservation reservation);

    /**
     * Getting all data to cache
     */
    void getData();
}
