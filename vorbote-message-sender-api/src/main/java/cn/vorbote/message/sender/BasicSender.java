package cn.vorbote.message.sender;

import cn.vorbote.message.model.MessageRequest;
import cn.vorbote.message.model.MessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * BasicSender<br>
 * Created at 02/09/2022 14:51
 *
 * @author theod
 */
public abstract class BasicSender {

    /**
     * Send a SMS.
     *
     * @param request The data to send a sms.
     * @return The response data from sent message.
     * @throws JsonProcessingException ObjectMapper could make this exception because
     *                                 of the data is not serializable.
     */
    public abstract MessageResponse send(MessageRequest request) throws JsonProcessingException;

    /**
     * Send several messages to multiple recipients.
     *
     * @param request The data to send a sms.
     * @return The response data from sent message.
     */
    public abstract MessageResponse batchSend(MessageRequest request);

}
