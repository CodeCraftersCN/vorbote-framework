package cn.vorbote.core.exceptions;

/**
 * This exception means the specified jwt algorithm is not supported.
 *
 * @author vorbote
 * @since 3.0.0
 */
public class UnsupportedHashAlgorithmException extends RuntimeException {

    /**
     * Exception message.
     */
    private final String message;

    /**
     * Create a {@code UnsupportedHashAlgorithmException} instance with algorithm name.
     *
     * @param algorithm the name of algorithm
     */
    public UnsupportedHashAlgorithmException(String algorithm) {
        message = String.format("Unsupported algorithm %s.", algorithm);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
