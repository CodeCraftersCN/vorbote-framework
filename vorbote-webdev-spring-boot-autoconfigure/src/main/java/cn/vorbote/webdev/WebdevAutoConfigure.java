package cn.vorbote.webdev;

import cn.vorbote.simplejwt.AccessKeyUtil;
import cn.vorbote.simplejwt.choices.JwtAlgorithm;
import cn.vorbote.web.filter.CorsFilter;
import cn.vorbote.webdev.cors.CorsProperties;
import cn.vorbote.webdev.jwt.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web Dev Suite auto configurer.
 *
 * @author vorbote thills@vorbote.cn
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = {JwtProperties.class, CorsProperties.class})
public class WebdevAutoConfigure {

    private final JwtProperties jwtProperties;

    private final CorsProperties corsProperties;

    @Autowired
    public WebdevAutoConfigure(JwtProperties jwtProperties, CorsProperties corsProperties) {
        this.jwtProperties = jwtProperties;
        this.corsProperties = corsProperties;
    }

    @Bean
    @ConditionalOnProperty(name = "vorbote.web-dev.jwt.enabled", havingValue = "true")
    public AccessKeyUtil accessKeyUtil() {
        log.debug("Building JSON Web Token handler.");
        JwtProperties info = this.jwtProperties;
        JwtAlgorithm algorithm = info.getAlgorithm();
        if (algorithm == null) {
            algorithm = JwtAlgorithm.HS256;
        }
        return new AccessKeyUtil(algorithm, info.getSecret(), info.getIssuer());
    }

    @Bean
    @ConditionalOnProperty(name = "vorbote.web-dev.cors.enabled", havingValue = "true")
    public CorsFilter corsFilter() {
        log.debug("Building CORS Filter...");
        if (corsProperties.getAllowCredentials() == null) {
            log.warn("Detected that the property Allow-Credentials is null, it will be set to false by default.");
            corsProperties.setAllowCredentials(false);
        }
        return new CorsFilter(corsProperties.getAllowCredentials(), corsProperties.getAllowOrigin(),
                corsProperties.getAllowMethods(), corsProperties.getAllowHeaders(), corsProperties.getExposeHeaders());
    }
}
