import app.DoMeApp;
import app.DoMeAuthManager;
import core.App;
import core.AuthorizationManager;
import ui.UI;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        AuthorizationManager manager = new DoMeAuthManager();
        App app = new DoMeApp();
        UI ui = new UI(manager, app);

        boolean loggedIn = ui.logIn("test", "test");
        if (loggedIn) {
            ui.makeReservation(5, 5, 2021, 21, 0);
            ui.printReservations();
        }
    }
}
