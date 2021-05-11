package core;

import app.User;

public interface UserRepository {
    User get(String email);
    User add(User user);
    void getData();
}
