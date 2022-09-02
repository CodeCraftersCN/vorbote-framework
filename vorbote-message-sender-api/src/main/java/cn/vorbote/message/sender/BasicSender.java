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

    public abstract MessageResponse send(MessageRequest request) throws JsonProcessingException;

    public abstract MessageResponse batchSend(MessageRequest request);

}
