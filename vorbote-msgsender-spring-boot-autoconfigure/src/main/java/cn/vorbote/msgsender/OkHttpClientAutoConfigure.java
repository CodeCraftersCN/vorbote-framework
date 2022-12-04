package cn.vorbote.msgsender;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OkHttpClientAutoConfigure<br>
 * Created at 04/12/2022 14:44
 *
 * @author vorbote
 */
@Slf4j
@Configuration
@ConditionalOnMissingBean(value = {OkHttpClient.class})
public class OkHttpClientAutoConfigure {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
