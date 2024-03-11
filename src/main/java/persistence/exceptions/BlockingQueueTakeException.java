package persistence.exceptions;

public class BlockingQueueTakeException extends RuntimeException {

    public BlockingQueueTakeException(String reason) {
        super(reason);
    }
}