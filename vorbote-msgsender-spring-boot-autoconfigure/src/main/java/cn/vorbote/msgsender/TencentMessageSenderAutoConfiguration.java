package cn.vorbote.msgsender;

import cn.vorbote.message.sender.IMessageSender;
import cn.vorbote.message.sender.tencent.TencentSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * TencentMessageSenderAutoConfiguration<br>
 * Created at 04/09/2022 18:47
 *
 * @author theod
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = {TencentMessageSenderProperties.class})
@ConditionalOnClass(TencentSender.class)
@ConditionalOnProperty(value = "vorbote.msg-sender.tencent.enabled", havingValue = "true")
public class TencentMessageSenderAutoConfiguration {

    private final TencentMessageSenderProperties senderProperties;

    @Autowired
    public TencentMessageSenderAutoConfiguration(TencentMessageSenderProperties senderProperties) {
        this.senderProperties = senderProperties;
    }

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public IMessageSender<List<String>> tencentSender() {
        return new TencentSender(senderProperties.getRegion(), senderProperties.getAppId(),
                senderProperties.getSecretId(), senderProperties.getSecretKey(), objectMapper);
    }
}
