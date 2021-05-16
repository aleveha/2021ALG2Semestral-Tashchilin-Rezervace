package ui;

import core.Reservation;
import core.User;
import core.App;
import core.AuthorizationManager;
import core.UserDoesNotExistException;
import utils.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

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
        String startMsg = "\nWelcome to COVID-19 reservation system!\nFollow instructions below to make a reservation.\n";
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
            String email = getEmail();
            String password = getField("password");

            try {
                this.currentUser = authManager.logIn(email, password);
                System.out.print(
                        currentUser != null ?
                                "You are successfully logged in.\n\n":
                                "Wrong login or password.\nDo you want to try again?(t/n):"
                );
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

        String firstname = getField("firstname");
        String lastname = getField("lastname");
        int age = getAge();
        String email = getEmail();
        String pwd = getPassword();

        User newUser = new User(email, pwd, firstname, lastname, age);
        currentUser = authManager.signIn(newUser);

        System.out.println(currentUser != null ?
                "You are successfully signed in.\n" :
                "User with same email already exists.\n"
        );
        if (currentUser == null) {
            entryForm();
        }
    }

    private void logOut() {
        currentUser = authManager.logOut();
        System.out.println(currentUser == null ?
                "You are successfully logged out.\n" :
                "Something went wrong.\n"
        );
    }

    private String getField(String name) {
        String field;
        boolean incorrectField;

        do {
            System.out.print("Enter " + name + ": ");
            field = sc.nextLine();
            incorrectField = field.length() < 2 || field.length() > 50;

            if (incorrectField) {
                System.out.printf("Incorrect %s! Value must contain from 2 to 50 characters. Try again:\n", name);
            }
        }while (incorrectField);

        return field;
    }

    private int getAge() {
        int age;
        boolean unrealAge;

        do {
            System.out.print("Enter age: ");
            age = sc.nextInt();
            sc.nextLine();
            unrealAge = age < 1 || age > 110;

            if (unrealAge) {
                System.out.println("Incorrect age! Try again:");
            }
        } while (unrealAge);

        return age;
    }

    private String getEmail() {
        String email;
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        boolean incorrectEmailFormat;

        do {
            email = getField("e-mail");
            incorrectEmailFormat = !pattern.matcher(email).matches();

            if (incorrectEmailFormat) {
                System.out.println("Incorrect e-mail format! Try again.");
            }
        } while (incorrectEmailFormat);

        return email;
    }

    private String getPassword() {
        String pwd, submitPwd;
        boolean pwdDoNotMatch;

        do {
            pwd = getField("password");
            submitPwd = getField("control of password");

            pwdDoNotMatch = !pwd.equals(submitPwd) || pwd.length() < 1;

            if (pwdDoNotMatch) {
                System.out.println("Passwords do not match! Try again.");
            }
        } while (pwdDoNotMatch);

        return pwd;
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

        System.out.println("\nProcessing your reservation...\n");
        Reservation reservation = app.makeReservation(new Reservation(DateTimeParser.dateToString(date), DateTimeParser.timeToString(time), currentUser));
        System.out.println(reservation == null ? "\nThis term is already taken." : "\nYour new reservation successfully ended!\nYou will find information about it below.\n\n" + reservation);
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
