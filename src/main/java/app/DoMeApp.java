package app;

import core.App;
import core.ReadingFileException;
import core.ReservationRepository;
import persistence.DoMeReservationRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DoMeApp extends App {
    private final ReservationRepository reservationRepository = new DoMeReservationRepository();

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
    private static void sendEmail(Reservation reservation) {
        User user = reservation.getUser();
        String emailTo = reservation.getUser().getEmail();
        String title = "Covid-19 test reservation";
        String message = replaceTemplateVariables(readTemplate(), user, reservation, title);

        OutlookEmailSender emailSender = new OutlookEmailSender(emailTo, message, title);
        emailSender.sendEmail();
    }

    /**
     * Reading HTML email template
     * @return HTML email template as string
     */
    private static String readTemplate() {
        StringBuilder contentBuilder = new StringBuilder();

        try {
            BufferedReader in = new BufferedReader(new FileReader("src/dataStore/emailTemplate.html"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException ex) {
            throw new ReadingFileException("Problems with reading the file.\n" + ex.getMessage());
        }

        return contentBuilder.toString();
    }

    /**
     * Creating email content
     * @param template email template
     * @param user user
     * @param reservation reservation
     * @param title email title
     * @return complete email content from email template
     */
    private static String replaceTemplateVariables(String template, User user, Reservation reservation, String title) {
        return template
                .replace("$title", title)
                .replace("$name", user.getFirstName())
                .replace("$lastName", user.getLastName())
                .replace("$date", reservation.getDate())
                .replace("$time", reservation.getTime())
                .replace("$age", Integer.toString(user.getAge()));
    }
}
