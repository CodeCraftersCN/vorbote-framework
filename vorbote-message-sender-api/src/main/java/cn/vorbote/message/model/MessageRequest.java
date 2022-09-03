package cn.vorbote.message.model;

import cn.vorbote.message.util.JacksonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @param appId        App ID, this field will only be used in <b>Tencent Cloud</b>.
 * @param sign         Sign name.
 * @param templateCode The sms template code.
 * @param params       The params of the data.
 * @param receivers    The recipients.
 */
public record MessageRequest(
        String appId,
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
