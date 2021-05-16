package core;

import app.Reservation;

@FunctionalInterface
public interface IEmailSender {
     void sendEmail(Reservation reservation);
}
