package core;

public class UserDoesNotExistException extends RuntimeException {
    /**
     * User does not exist exception
     * @param msg error message
     */
    public UserDoesNotExistException(String msg) {
        super(msg);
    }
}
