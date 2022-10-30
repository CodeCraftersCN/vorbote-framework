package cn.vorbote.message.sender;

import cn.vorbote.message.model.BatchMessageRequest;
import cn.vorbote.message.model.MessageRequest;
import cn.vorbote.message.model.MessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * IMessage Sender interface.<br>
 * Created at 02/09/2022 14:51
 *
 * @author theod
 */
public interface IMessageSender<T> {

    /**
     * Send a SMS.
     *
     * @param request The data to send a sms.
     * @return The response data from sent message.
     * @throws JsonProcessingException ObjectMapper could make this exception because
     *                                 of the data is not serializable.
     */
    MessageResponse send(MessageRequest<T> request) throws JsonProcessingException;

    /**
     * Send several messages to multiple recipients.
     *
     * @param request The data to send a sms.
     * @return The response data from sent message.
     */
    MessageResponse batchSend(BatchMessageRequest<T> request);

}
