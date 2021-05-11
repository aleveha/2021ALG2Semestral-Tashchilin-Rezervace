package core;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(String msg) {
        super(msg);
    }
}
