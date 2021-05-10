package core;

import app.User;

public abstract class AuthorizationManager {
    public abstract User login(String email, String password);
    public abstract User logout();
}
