package app;

import core.App;
import core.ReservationRepository;
import persistence.DoMeReservationRepository;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.List;
import java.util.Properties;

public class DoMeApp extends App {
    private final ReservationRepository reservationRepository = new DoMeReservationRepository();

    @Override
    public Reservation makeReservation(Reservation reservation) {
        reservationRepository.getData();

        if (reservationRepository.add(reservation) != null) {
            sendEmail(reservation);
            return reservation;
        }

        return null;
    }

    @Override
    public List<Reservation> getReservations(User user) {
        reservationRepository.getData();
        return reservationRepository.getAll(user.getEmail());
    }

    private void sendEmail(Reservation reservation) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.mailtrap.io");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("e455f6b6cac32f", "46c62a74f2204d");
            }
        });

        User user = reservation.getUser();
        String emailFrom = "covid_19_test_reservation@outlook.com";
        String emailTo = user.getEmail();
        String message = String.format(
                """
                        Hello, %s %s!

                        Your have been successfully registered to Covid-19 test.
                        You will find all information below

                        %s
                        
                        With best regards,
                        Covid-19 test reservation system""",
                user.getLastName(),
                user.getFirstName(),
                reservation
        );

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailFrom));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            msg.setSubject("Covid-19 test reservation");

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(message, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            msg.setContent(multipart);

            Transport.send(msg);
        } catch (MessagingException messagingException) {
            throw new RuntimeException("Error occurred while sending a message!");
        }
    }
}
