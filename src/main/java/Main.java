import app.AppImpl;
import app.AuthManagerImpl;
import core.App;
import core.AuthorizationManager;
import ui.UI;

public class Main {
    public static void main(String[] args) {
        AuthorizationManager manager = new AuthManagerImpl();
        App app = new AppImpl();
        UI ui = new UI(manager, app);

        ui.run();
    }
}
