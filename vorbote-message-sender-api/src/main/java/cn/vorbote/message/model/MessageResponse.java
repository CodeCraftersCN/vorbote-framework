package cn.vorbote.message.model;

/**
 * MessageResponse<br>
 * Created at 30/10/2022 00:34
 *
 * @author vorbote
 */
public final class MessageResponse {

    private String message;

    private String code;

    private MessageResponse(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public static MessageResponse initResponse(String code, String message) {
        return new MessageResponse(code, message);
    }
}
