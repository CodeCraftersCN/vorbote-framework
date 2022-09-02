package cn.vorbote.message.model;

/**
 * SendResponse<br>
 * Created at 02/09/2022 14:52
 *
 * @author theod
 */
public record MessageResponse(
        String message,
        String requestId,
        String code,
        String bizId) {
}
