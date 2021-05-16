package core;

import app.User;

public abstract class AuthorizationManager {
    /**
     * Logging in
     * @param email user's email
     * @param password user's password
     * @return new user
     */
    public abstract User logIn(String email, String password);

    /**
     * Logging out
     * @return user or null
     */
    public abstract User logOut();

    /**
     * Signing in
     * @param user user
     * @return new user
     */
    public abstract User signIn(User user);
}
