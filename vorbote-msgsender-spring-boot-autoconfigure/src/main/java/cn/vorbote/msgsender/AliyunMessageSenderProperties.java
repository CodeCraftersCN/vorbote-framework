package cn.vorbote.msgsender;

import cn.vorbote.message.sender.aliyun.config.AliyunRegion;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * These are the properties to define a {@link cn.vorbote.message.sender.aliyun.AliyunSender} instance.<br>
 * Created at 19/12/2022 01:07
 *
 * @author theod
 */
@Data
@ConfigurationProperties(prefix = "vorbote.msg-sender.aliyun")
public class AliyunMessageSenderProperties {

    /**
     * This property is used to configure whether to use the Tencent Cloud Platform SMS service.
     */
    private Boolean enabled;

    /**
     * Configure the geographic region to which Tencent Cloud belongs.
     */
    private AliyunRegion region;

    /**
     * Configure the sign name for sending a message.
     */
    private String sign;

    /**
     * Token ID for accessing Aliyun Cloud accounts, this property is called as {@code AccessKeyId} by aliyun cloud,
     * you can go to <a href="https://www.aliyun.com/product/ram">their website</a> for details.
     */
    private String secretId;

    /**
     * Token Key for accessing Aliyun Cloud accounts, this property is called as {@code AccessKeySecret} by aliyun
     * cloud.
     */
    private String secretKey;

}
