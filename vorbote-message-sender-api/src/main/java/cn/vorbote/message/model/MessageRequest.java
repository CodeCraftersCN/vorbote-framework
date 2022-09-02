package cn.vorbote.message.model;

import cn.vorbote.message.util.JacksonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * SendRequest<br>
 * Created at 02/09/2022 14:52
 *
 * @author theod
 */
public record MessageRequest(
        String sign,
        String templateCode,
        Object params,
        String... receivers) {

    public String getReceivers() {
        var builder = new StringBuilder();
        for (var receiver : receivers) {
            builder.append(receiver).append(",");
        }
        return builder.substring(0, builder.length() - 1);
    }

    public String getParams() throws JsonProcessingException {
        return JacksonSerializer.getInstance().serialize(params);
    }
}
