package app;

import core.AuthorizationManager;
import core.User;
import core.UserDoesNotExistException;
import core.UserRepository;
import persistence.UserRepositoryImpl;

public class AuthManagerImpl extends AuthorizationManager {
    private final UserRepository userRepository = new UserRepositoryImpl();

    /**
     * Logging in
     * @param email user's email
     * @param password user's password
     * @return user if successfully logged in, else return null
     */
    @Override
    public User logIn(String email, String password) {
        userRepository.getData();
        User user = userRepository.get(email);
        if (user == null) {
            throw new UserDoesNotExistException("The user does not exist.");
        }

        if (email.equalsIgnoreCase(user.getEmail()) && password.equalsIgnoreCase(user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * Logging out
     * @return null
     */
    @Override
    public User logOut() {
        return null;
    }

    /**
     * Signing in
     * @param user user
     * @return user if succesfully signed in, else return null
     */
    @Override
    public User signIn(User user) {
        userRepository.getData();
        return userRepository.add(user);
    }
}
