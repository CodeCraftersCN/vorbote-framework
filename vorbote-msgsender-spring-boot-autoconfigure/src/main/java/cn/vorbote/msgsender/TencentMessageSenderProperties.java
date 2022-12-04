package cn.vorbote.msgsender;

import cn.vorbote.message.sender.tencent.config.TencentRegion;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TencentMessageSenderProperties<br>
 * Created at 04/09/2022 18:46
 *
 * @author theod
 */
@Data
@ConfigurationProperties(prefix = "vorbote.msg-sender.tencent")
public class TencentMessageSenderProperties {

    /**
     * This property is used to configure whether to use the Tencent Cloud Platform SMS service.
     */
    private Boolean enabled;

    /**
     * Configure the geographic region to which Tencent Cloud belongs.
     */
    private TencentRegion region;

    /**
     * Configure the sign name for sending a message.
     */
    private String sign;

    /**
     * Token ID for accessing Tencent Cloud accounts.
     */
    private String secretId;

    /**
     * Token Key for accessing Tencent Cloud accounts.
     */
    private String secretKey;

    /**
     * The ID of the current application registered on the Tencent Cloud platform.
     */
    private String appId;
}
