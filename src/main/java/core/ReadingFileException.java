package core;

public class ReadingFileException extends RuntimeException {
    /**
     * Reading file exception
     * @param msg error message
     */
    public ReadingFileException(String msg) {
        super(msg);
    }
}
