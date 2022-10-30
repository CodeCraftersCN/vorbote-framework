package cn.vorbote.message.model;

import java.util.List;

/**
 * BatchMessageRequest<br>
 * Created at 30/10/2022 00:52
 *
 * @author vorbote
 */
public final class BatchMessageRequest<T> {

    private final String sign;
    private final String templateId;
    private final List<String> receivers;
    private final T params;

    private BatchMessageRequest(String sign, String templateId, List<String> receivers, T params) {
        this.sign = sign;
        this.templateId = templateId;
        this.receivers = receivers;
        this.params = params;
    }

    public static <T> BatchMessageRequest<T> createRequest(String sign, String templateId, List<String> receivers, T params) {
        return new BatchMessageRequest<>(sign, templateId, receivers, params);
    }

    public String sign() {
        return sign;
    }

    public String templateId() {
        return templateId;
    }

    public T params() {
        return params;
    }

    public List<String> receivers() {
        return receivers;
    }

}
