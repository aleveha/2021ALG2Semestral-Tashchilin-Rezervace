package app;

import core.AuthorizationManager;

public class DoMeAuthManager extends AuthorizationManager {
    @Override
    public User login(String login, String password) {
        if (login.equals("test") && password.equals("test")) {
            return new User("test@gmail.com", "test", "Test", "Testovich", "test", 20);
        }
        return null;
    }

    @Override
    public void logout() {
        System.out.println("Logged out");
    }
}
