package core;

import app.User;

public abstract class AuthorizationManager {
    public abstract User logIn(String email, String password);
    public abstract User logOut();
    public abstract User signIn(User user);
}
