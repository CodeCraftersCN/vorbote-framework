package cn.vorbote.core.exceptions;

/**
 * SnowFlakeException<br>
 * Created at Jun 19, 2022 00:12:12 AM
 *
 * @author vorbote
 */
public class SnowFlakeException extends RuntimeException {

    /**
     * Default constructor
     */
    public SnowFlakeException() {
    }

    /**
     * Create a {@code SnowFlakeException} with a message.
     *
     * @param message the exception message
     */
    public SnowFlakeException(String message) {
        super(message);
    }
}
