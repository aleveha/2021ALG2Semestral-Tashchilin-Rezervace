package app;

import core.AuthorizationManager;
import core.UserDoesNotExistException;
import core.UserRepository;
import persistence.DoMeUserRepository;

public class DoMeAuthManager extends AuthorizationManager {
    private final UserRepository userRepository = new DoMeUserRepository();

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

    @Override
    public User logOut() {
        return null;
    }

    @Override
    public User signIn(User user) {
        userRepository.getData();
        return userRepository.add(user);
    }
}
