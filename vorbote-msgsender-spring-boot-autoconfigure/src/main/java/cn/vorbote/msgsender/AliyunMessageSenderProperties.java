package cn.vorbote.msgsender;

import cn.vorbote.message.config.AliyunRegion;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MessageSenderProperties<br>
 * Created at 04/09/2022 14:33
 *
 * @author theod
 */
@Data
@ConfigurationProperties(prefix = "vorbote.msg-sender.aliyun")
public class AliyunMessageSenderProperties {

    /**
     * This property is used to configure whether to use the Aliyun Platform SMS service.
     */
    private Boolean enabled;

    /**
     * Configure the geographic region to which Aliyun belongs.
     */
    private AliyunRegion region;

    /**
     * Token ID for accessing Aliyun accounts.
     */
    private String secretId;

    /**
     * Token Key for accessing Aliyun accounts.
     */
    private String secretKey;

}
