package cn.vorbote.msgsender;

import cn.vorbote.message.config.MessageSenderEngine;
import cn.vorbote.message.config.Region;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MessageSenderProperties<br>
 * Created at 04/09/2022 14:33
 *
 * @author theod
 */
@Data
@ConfigurationProperties(prefix = "vorbote.msg-sender")
public class MessageSenderProperties {

    private Boolean enabled;

    private MessageSenderEngine engine;

    private String secretId;

    private String secretKey;

    private Region region;

    private String appId;

}
