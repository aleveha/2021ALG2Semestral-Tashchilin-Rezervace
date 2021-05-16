package core;

import app.User;

public interface UserRepository {
    /**
     * Getting user information
     * @param email user's email
     * @return user information
     */
    User get(String email);

    /**
     * Adding a new user
     * @param user user
     * @return user if user is successfully added, else return null
     */
    User add(User user);

    /**
     * Getting data to cache
     */
    void getData();
}
