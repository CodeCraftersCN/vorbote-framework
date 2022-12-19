package cn.vorbote.msgsender;

import cn.vorbote.message.auth.UserProfile;
import cn.vorbote.message.sender.aliyun.AliyunSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This is a auto configurer to create a {@link AliyunSender} instance.<br>
 * Created at 04/09/2022 18:47
 *
 * @author theod
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = {AliyunMessageSenderProperties.class})
@ConditionalOnClass(AliyunSender.class)
@ConditionalOnProperty(value = "vorbote.msg-sender.aliyun.enabled", havingValue = "true")
public class AliyunMessageSenderAutoConfigure {

    private final AliyunMessageSenderProperties senderProperties;

    @Autowired
    public AliyunMessageSenderAutoConfigure(AliyunMessageSenderProperties senderProperties) {
        this.senderProperties = senderProperties;
    }

    private ObjectMapper objectMapper;

    private OkHttpClient okHttpClient;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    /**
     * Create a default Aliyun Sender with {@link ObjectMapper} instance in the SpringBoot and a new
     * {@link OkHttpClient}.
     *
     * @return a default {@link AliyunSender} instance
     */
    @Bean
    public AliyunSender aliyunSender() {
        return new AliyunSender(
                UserProfile.createProfile(
                        senderProperties.getSecretId(),
                        senderProperties.getSecretKey()),
                senderProperties.getSign(),
                objectMapper,
                okHttpClient);
    }
}
