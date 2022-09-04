package cn.vorbote.msgsender;

import cn.vorbote.message.sender.aliyun.AliyunSender;
import cn.vorbote.message.sender.tencent.TencentSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MessageSenderAutoConfigure<br>
 * Created at 04/09/2022 14:32
 *
 * @author theod
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = {MessageSenderProperties.class})
@ConditionalOnProperty(value = "vorbote.msg-sender.enabled", havingValue = "true")
public class MessageSenderAutoConfigure {

    private final MessageSenderProperties senderProperties;

    @Autowired
    public MessageSenderAutoConfigure(MessageSenderProperties senderProperties) {
        this.senderProperties = senderProperties;
    }

    @Bean
    @ConditionalOnProperty(value = "vorbote.msg-sender.engine", havingValue = "aliyun")
    @ConditionalOnClass(AliyunSender.class)
    public AliyunSender aliyunSender() {
        log.info("Creating ALIYUN Sender");
        return new AliyunSender(senderProperties.getRegion(),
                senderProperties.getSecretId(), senderProperties.getSecretKey());
    }

    @Bean
    @ConditionalOnProperty(value = "vorbote.msg-sender.engine", havingValue = "tencent")
    @ConditionalOnClass(TencentSender.class)
    public TencentSender tencentSender() {
        log.info("Creating TENCENT Sender");
        return new TencentSender(senderProperties.getAppId(),
                senderProperties.getSecretId(), senderProperties.getSecretKey());
    }
}
