package cn.vorbote.message.model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * MessageResponse<br>
 * Created at 30/10/2022 00:34
 *
 * @author vorbote
 */
@Getter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class MessageResponse {

    private final String message;
    private final String code;

    private MessageResponse(String code, String message) {
        this.message = message;
        this.code = code;
    }

    /**
     * Initialize a message response with a code and a message.
     *
     * @param code    the code from the cloud service platform.
     * @param message the message from the cloud service platform.
     * @return a response of sending a message.
     */
    public static MessageResponse initResponse(String code, String message) {
        return new MessageResponse(code, message);
    }

    /**
     * Get the message from the server.
     *
     * @return the message from the server.
     */
    public String message() {
        return message;
    }

    /**
     * Get the code sent from server.
     *
     * @return the code from server.
     */
    public String code() {
        return code;
    }

    /**
     * Get the message from the server.
     *
     * @return the message from the server.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the code sent from server.
     *
     * @return the code from server.
     */
    public String getCode() {
        return code;
    }
}
