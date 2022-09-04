package cn.vorbote.message.model;

/**
 * When sent a message via SMS Sender, the server will respond some data.
 *
 * @param message The status message.
 * @param code    The status code.
 */
public record MessageResponse(
        String message,
        String code) {
}
