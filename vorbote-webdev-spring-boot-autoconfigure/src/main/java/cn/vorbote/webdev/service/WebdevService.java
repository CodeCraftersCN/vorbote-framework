package cn.vorbote.webdev.service;

import cn.vorbote.webdev.cors.CorsConfigurationInfo;
import cn.vorbote.webdev.jwt.JwtConfigurationInfo;

/**
 * This is the main service class.
 *
 * @author vorbote thills@vorbote.cn
 */
public interface WebdevService {

    /**
     * Build JWT configuration info.
     *
     * @return {@link JwtConfigurationInfo} instance.
     */
    JwtConfigurationInfo jwtConfigurationInfo();

    /**
     * Build {@code CORS} configuration info.
     *
     * @return {@link CorsConfigurationInfo} instance.
     */
    CorsConfigurationInfo corsConfigurationInfo();

}
