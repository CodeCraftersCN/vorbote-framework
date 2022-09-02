package cn.vorbote.core.exceptions;

/**
 * If the value of the specific field in time is out of its correct range, this exception will be thrown.<br>
 * For example, if you set the field "minute" to 60 will cause this exception.
 *
 * @author vorbote
 */
public class TimeOutRangeException extends RuntimeException {

    /**
     * Message of this exception, to tell the coder which field is wrong.
     */
    private final String message;

    /**
     * Constructor, to build a clear exception message.
     *
     * @param field               The field which is set to be a wrong value.
     * @param correctMinimumValue The correct minimum value of this field.
     * @param correctMaximumValue The correct maximum value of this field.
     */
    public TimeOutRangeException(String field, Integer correctMinimumValue, Integer correctMaximumValue) {
        message = String.format("The %s is out of range of (%d ~ %d).", field, correctMinimumValue,
                correctMaximumValue);
    }

    /**
     * Convert to a {@code String} to make this exception clear enough for coders.
     *
     * @return A {@code String} includes what went wrong.
     */
    @Override
    public String toString() {
        return message;
    }

    /**
     * Convert to a {@code String} to make this exception clear enough for coders.
     *
     * @return A {@code String} includes what went wrong.
     * @see #toString()
     */
    @Override
    public String getMessage() {
        return message;
    }
}
