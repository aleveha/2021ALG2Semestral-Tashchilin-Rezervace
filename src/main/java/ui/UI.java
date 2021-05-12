package ui;

import app.Reservation;
import app.User;
import core.App;
import core.AuthorizationManager;
import core.UserDoesNotExistException;
import utils.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static utils.DateTimeParser.datePattern;
import static utils.DateTimeParser.timePattern;

public class UI {
    public Scanner sc = new Scanner(System.in);
    private final AuthorizationManager authManager;
    private final App app;
    User currentUser = null;

    public UI(AuthorizationManager authManager, App app) {
        this.authManager = authManager;
        this.app = app;
    }

    public void run() {
        startMessage();
        entryForm();
        reservationForm();
        endMessage();
    }

    private void startMessage() {
        String startMsg = "Welcome to COVID-19 reservation system!\nFollow instructions below to make a reservation.\n";
        System.out.println(startMsg);
    }

    private void loginMessage() {
        String startMsg = "Seems like you're not logged in.\nEnter 'L' to log in.\nEnter 'R' to sign in.\nEnter 'X' to exit.";
        System.out.println(startMsg);
    }

    private void logIn() {
        System.out.println("\nLOG IN FORM:");

        String tryAgain;
        do {
            System.out.print("Enter email: ");
            String login = sc.nextLine();

            System.out.print("Enter password: ");
            String password = sc.nextLine();

            try {
                this.currentUser = authManager.logIn(login, password);
                System.out.print(currentUser != null ? "You are successfully logged in.\n\n" : "Wrong login or password.\nDo you want to try again?(t/n):");
            } catch (UserDoesNotExistException ex) {
                System.out.println(ex.getMessage());
                System.out.print("Do you want to try again, register or exit?(t/r/n):");
            }

            tryAgain = currentUser == null ? sc.nextLine() : "n";
            if (tryAgain.equalsIgnoreCase("r")) {
                signIn();
                break;
            }
        } while (currentUser == null && tryAgain.trim().equalsIgnoreCase("t"));
    }

    private void signIn() {
        System.out.println("\nSIGN IN FORM:");

        System.out.print("Enter firstname: ");
        String firstname = sc.nextLine();

        System.out.print("Enter lastname: ");
        String lastname = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter e-mail: ");
        String email = sc.nextLine();

        String pwd, submit_pwd;
        do {
            System.out.print("Enter password: ");
            pwd = sc.nextLine();

            System.out.print("Submit password: ");
            submit_pwd = sc.nextLine();

            if (!pwd.equals(submit_pwd)) {
                System.out.println("Passwords do not match!");
            }
        } while (!pwd.equals(submit_pwd));

        User newUser = new User(email, pwd, firstname, lastname, age);
        currentUser = authManager.signIn(newUser);

        System.out.println(currentUser != null ? "You are successfully signed in.\n" : "User with same email already exists.\n");
        if (currentUser == null) {
            entryForm();
        }
    }

    private void logOut() {
        currentUser = authManager.logOut();
        System.out.println(currentUser == null ? "You are successfully logged out.\n" : "Something went wrong.\n");
    }

    private void entryForm() {
        loginMessage();
        String enterChoice = sc.nextLine();
        switch (enterChoice.toLowerCase(Locale.ROOT)) {
            case "l" -> logIn();
            case "r" -> signIn();
            case "x" -> logOut();
            default -> System.out.println("Wrong button. Try again.");
        }
    }

    private void actionChoiceMessage() {
        System.out.println("Enter 'M' to make a new reservation.\nEnter 'P' to show all your reservations.\nEnter 'X' to exit.");
    }

    private void makeReservation() {
        System.out.println("MAKE RESERVATION FORM:");
        LocalDate date = null;
        LocalTime time = null;

        while (date == null) {
            System.out.printf("Enter date using format (%s): ", datePattern.toUpperCase(Locale.ROOT));
            String inputDate = sc.nextLine();

            date = DateTimeParser.parseDate(inputDate);
            if (date == null) {
                System.out.println("Invalid date. Try again.");
            }
        }

        while (time == null) {
            System.out.printf("Enter time using format (%s): ", timePattern.toUpperCase(Locale.ROOT));
            String inputTime = sc.nextLine();

            time = DateTimeParser.parseTime(inputTime);
            if (time == null) {
                System.out.println("Invalid time. Try again.");
            }
        }

        Reservation reservation = app.makeReservation(new Reservation(DateTimeParser.dateToString(date), DateTimeParser.timeToString(time), currentUser));
        System.out.println(reservation == null ? "This term is already taken." : "\nYour new reservation successfully ended!\nYou will find information about it below.\n\n" + reservation);
    }

    private void printReservations() {
        List<Reservation> res = app.getReservations(currentUser);
        if (res.size() == 0) {
            System.out.println("You do not have reservations yet.\n");
        } else {
            System.out.println("\nRESERVATION LIST:");
            res.forEach(System.out::println);
        }
    }

    private void reservationForm() {
        System.out.println("MAIN MENU:");

        while (currentUser != null) {
            actionChoiceMessage();

            String actionChoice = sc.nextLine();
            if (actionChoice.equals("")) continue;
            switch (actionChoice.toLowerCase(Locale.ROOT)) {
                case "m" -> makeReservation();
                case "p" -> printReservations();
                case "x" -> entryForm();
                default -> System.out.println("Wrong button. Try again.");
            }
        }
    }

    private void endMessage() {
        System.out.println("We will be waiting for you again!");
    }
}
