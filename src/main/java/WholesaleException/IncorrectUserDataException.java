package WholesaleException;

public class IncorrectUserDataException extends Exception {
    public IncorrectUserDataException() { super("Incorrect username or password"); }
    public IncorrectUserDataException(String message) { super(message); }
    public IncorrectUserDataException(String message, Throwable cause) { super(message, cause); }
    public IncorrectUserDataException(Throwable cause) { super(cause); }
}
