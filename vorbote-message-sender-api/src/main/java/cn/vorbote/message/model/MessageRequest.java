package cn.vorbote.message.model;

import java.util.List;

/**
 * MessageRequest<br>
 * Created at 30/10/2022 00:33
 *
 * @author vorbote
 */
public final class MessageRequest<T> {

    private final String sign;
    private final String templateId;
    private final String receiver;
    private final T params;

    private MessageRequest(String sign, String templateId, String receiver, T params) {
        this.sign = sign;
        this.templateId = templateId;
        this.receiver = receiver;
        this.params = params;
    }

    public static <T> MessageRequest<T> createRequest(String sign, String templateId, String receiver, T params) {
        return new MessageRequest<>(sign, templateId, receiver, params);
    }

    public String sign() {
        return sign;
    }

    public String templateId() {
        return templateId;
    }

    public String receiver() {
        return receiver;
    }

    public T params() {
        return params;
    }

    public String[] receivers() {
        return new String[]{receiver};
    }

}
