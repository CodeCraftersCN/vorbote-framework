package cn.vorbote.message.model;

import cn.vorbote.message.util.JacksonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * To send a SMS via SMS Sender, every single SMS need a MessageRequest.
 *
 * @param sign         Sign name.
 * @param templateCode The sms template code.
 * @param params       The params of the data.
 * @param receivers    The recipients.
 */
public record MessageRequest(
        String sign,
        String templateCode,
        Object params,
        String... receivers) {

    /**
     * Transfer receivers to specified format.
     *
     * @return Receivers in format of "&lt;The first receiver&gt;[,&lt;The second receiver&gt;[...]]"
     */
    public String getReceivers() {
        var builder = new StringBuilder();
        for (var receiver : receivers) {
            builder.append(receiver).append(",");
        }
        return builder.substring(0, builder.length() - 1);
    }

    /**
     * Transfer params to json string.
     *
     * @return A json string contains all param.
     * @throws JsonProcessingException {@link com.fasterxml.jackson.databind.ObjectMapper}
     *                                 will report this exception.
     */
    public String getParams() throws JsonProcessingException {
        return JacksonSerializer.getJacksonSerializer().serialize(params);
    }
}
