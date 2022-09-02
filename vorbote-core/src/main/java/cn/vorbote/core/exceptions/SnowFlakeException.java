package cn.vorbote.core.exceptions;

/**
 * SnowFlakeException<br>
 * Created at Jun 19, 2022 00:12:12 AM
 *
 * @author vorbote
 */
public class SnowFlakeException extends RuntimeException {

    public SnowFlakeException() {
    }

    public SnowFlakeException(String message) {
        super(message);
    }
}
