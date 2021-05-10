import app.DoMeApp;
import app.DoMeAuthManager;
import core.App;
import core.AuthorizationManager;
import ui.UI;

public class Main {
    public static void main(String[] args) {
        AuthorizationManager manager = new DoMeAuthManager();
        App app = new DoMeApp();
        UI ui = new UI(manager, app);

        ui.run();
    }
}
