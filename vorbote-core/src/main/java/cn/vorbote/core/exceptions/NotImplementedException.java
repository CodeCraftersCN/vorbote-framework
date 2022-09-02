package cn.vorbote.core.exceptions;

/**
 * If you have any feature which is not implemented, you can easily throw this exception.<br>
 * Created at 02/09/2022 16:09
 *
 * @author theod
 */
public class NotImplementedException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NotImplementedException(String message) {
        super(message);
    }
}
