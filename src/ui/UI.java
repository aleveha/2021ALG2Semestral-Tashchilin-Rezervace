package ui;

import app.Reservation;
import app.User;
import core.App;
import core.AuthorizationManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class UI {
    private final AuthorizationManager authManager;
    private final App app;
    User currentUser = null;

    public UI(AuthorizationManager authManager, App app) {
        this.authManager = authManager;
        this.app = app;
    }

    public boolean logIn(String login, String password) {
        this.currentUser = authManager.login(login, password);
        return this.currentUser != null;
    }

    public void makeReservation(int day, int month, int year, int hour, int minute) {
        if (currentUser == null) {
            unLoggedUser();
            return;
        }

        boolean success = app.makeReservation(currentUser, new Reservation(LocalDate.of(year, month, day), LocalTime.of(hour, minute), currentUser));
        if (success) {
            System.out.println("You have successfully registered!");
        } else {
            System.out.println("Something went wrong.");
        }
    }

    public void printReservations() {
        if (currentUser == null) {
            unLoggedUser();
            return;
        }

        List<Reservation> res = app.getReservations(currentUser);
        if (res.size() == 0) {
            System.out.println("You do not have reservations yet.");
        } else {
            res.forEach(System.out::println);
        }

    }

    private void unLoggedUser() {
        System.out.println("You have to log in.");
    }
}
