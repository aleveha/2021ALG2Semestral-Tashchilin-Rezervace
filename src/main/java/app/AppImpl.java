package app;

import core.App;
import core.IEmailSender;
import core.ReservationRepository;
import persistence.ReservationRepositoryImpl;

import java.util.List;

public class AppImpl extends App {
    private final ReservationRepository reservationRepository = new ReservationRepositoryImpl();
    private final IEmailSender outlookEmailSender = new OutlookEmailSender();

    /**
     * Making a new reservation
     * @param reservation reservation
     * @return reservation if reservation is successfully created, else return null
     */
    @Override
    public Reservation makeReservation(Reservation reservation) {
        reservationRepository.getData();

        if (reservationRepository.add(reservation) != null) {
            sendEmail(reservation);
            return reservation;
        }

        return null;
    }

    /**
     * Getting all user's reservations
     * @param user user
     * @return all user's reservations
     */
    @Override
    public List<Reservation> getReservations(User user) {
        reservationRepository.getData();
        return reservationRepository.getAll(user.getEmail());
    }

    /**
     * Sending reservation information by email
     * @param reservation reservation
     */
    private void sendEmail(Reservation reservation) {
        outlookEmailSender.sendEmail(reservation);
    }
}
