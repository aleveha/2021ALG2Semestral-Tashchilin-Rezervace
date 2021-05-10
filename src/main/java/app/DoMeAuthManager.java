package app;

import core.AuthorizationManager;

public class DoMeAuthManager extends AuthorizationManager {
    @Override
    public User login(String login, String password) {
        if (login.equals("test") && password.equals("test")) {
            return new User("test@gmail.com", "test", "Test", "Testovich", 20);
        }
        return null;
    }

    @Override
    public User logout() {
        return null;
    }
}
