package persistence.exceptions;

public class ConnectionException extends Exception {
    public ConnectionException(String reason) {
        super(reason);
    }
}
