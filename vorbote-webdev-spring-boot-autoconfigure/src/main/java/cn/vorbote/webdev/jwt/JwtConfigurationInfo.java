package cn.vorbote.webdev.jwt;

import cn.vorbote.simplejwt.choices.JwtAlgorithm;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * This is the configuration info class.
 *
 * @author vorbote thills@vorbote.cn
 */
@Data
@Builder
public class JwtConfigurationInfo implements Serializable {

    /**
     * Open the JWT Util by set this to {@code true}.
     */
    private final boolean enabled;

    /**
     * The issuer of jwt.
     */
    private String issuer;

    /**
     * The secret to use in jwt.
     */
    private String secret;

    /**
     * The algorithm of jwt
     */
    private JwtAlgorithm algorithm;



}
