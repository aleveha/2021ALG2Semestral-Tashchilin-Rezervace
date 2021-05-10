package ui;

import app.Reservation;
import app.User;
import core.App;
import core.AuthorizationManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

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
        loginMessage();
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
            System.out.print("Enter login: ");
            String login = sc.nextLine();

            System.out.print("Enter password: ");
            String password = sc.nextLine();

            this.currentUser = authManager.login(login, password);

            System.out.print(currentUser != null ? "You are successfully logged in.\n" : "Wrong login or password.\nDo you want to try again?(y/n):");
            tryAgain = currentUser == null ? sc.nextLine() : "n";
        } while (currentUser == null || tryAgain.trim().equalsIgnoreCase("y"));
    }

    private void signIn() {
        System.out.print("\nSIGN IN FORM:");

        System.out.print("Enter firstname: ");
        String firstname = sc.nextLine();

        System.out.print("Enter lastname: ");
        String lastname = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();

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

        currentUser = new User(email, pwd, firstname, lastname, age);

        System.out.println(currentUser != null ? "You are successfully signed in.\n" : "Something went wrong.\n");
    }

    private void logOut() {
        currentUser = authManager.logout();

        System.out.println(currentUser == null ? "You are successfully logged out.\n" : "Something went wrong.\n");
    }

    private void entryForm() {
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
        String datePattern = "dd.MM.yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        System.out.println("MAKE RESERVATION FORM:\n");

        System.out.printf("Enter date using format (%s): ", datePattern.toUpperCase(Locale.ROOT));
        String date = sc.nextLine();

        System.out.print("Enter time using format (HH:MM): ");
        String time = sc.nextLine();

        Reservation reservation = app.makeReservation(currentUser, new Reservation(LocalDate.parse(date, formatter), LocalTime.parse(time), currentUser));
        System.out.println(currentUser != null ? "\nYour new reservation successfully ended!\nYou will find information about it below.\n\n" + reservation : "Something went wrong.\n");
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
        System.out.println("\nMAIN MENU:");

        while (currentUser != null) {
            actionChoiceMessage();

            String actionChoice = sc.nextLine();
            if (actionChoice.equals("")) continue;
            switch (actionChoice.toLowerCase(Locale.ROOT)) {
                case "m" -> makeReservation();
                case "p" -> printReservations();
                case "x" -> logOut();
                default -> System.out.println("Wrong button. Try again.");
            }
        }
    }

    private void endMessage() {
        System.out.println("We will be waiting for you again!");
    }
}
