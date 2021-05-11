package app;

import core.App;
import core.ReservationRepository;
import persistence.DoMeReservationRepository;

import java.util.List;

public class DoMeApp extends App {
    private final ReservationRepository reservationRepository = new DoMeReservationRepository();

    @Override
    public Reservation makeReservation(Reservation reservation) {
        reservationRepository.getData();
        return reservationRepository.add(reservation);
    }

    @Override
    public List<Reservation> getReservations(User user) {
        reservationRepository.getData();
        return reservationRepository.getAll(user.getEmail());
    }
}
