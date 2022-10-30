package cn.vorbote.message.model;

import java.util.List;

/**
 * A message request is used to specify what to send and who to receive.<br>
 * Created at 30/10/2022 00:33
 *
 * @author vorbote
 */
public final class MessageRequest<T> {

    private final String templateId;
    private final String receiver;
    private final T params;

    private MessageRequest(String templateId, String receiver, T params) {
        this.templateId = templateId;
        this.receiver = receiver;
        this.params = params;
    }

    /**
     * Create a send-message request.
     *
     * @param templateId the template id on the specified cloud-service platform.
     * @param receiver   the phone number to receive this message.
     * @param params     the params to inject to the template.
     * @param <T>        the type of the param type.
     * @return a sending message request.
     */
    public static <T> MessageRequest<T> createRequest(String templateId, String receiver, T params) {
        return new MessageRequest<>(templateId, receiver, params);
    }

    /**
     * Get the template ID.
     *
     * @return the template id.
     */
    public String templateId() {
        return templateId;
    }

    /**
     * Get the receiver.
     *
     * @return the receiver.
     */
    public String receiver() {
        return receiver;
    }

    /**
     * Get the params.
     *
     * @return the params.
     */
    public T params() {
        return params;
    }

    /**
     * Get the template ID.
     *
     * @return the template id.
     * @see #templateId()
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * Get the receiver.
     *
     * @return the receiver.
     * @see #receiver()
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Get the params.
     *
     * @return the params.
     * @see #params()
     */
    public T getParams() {
        return params;
    }
}
