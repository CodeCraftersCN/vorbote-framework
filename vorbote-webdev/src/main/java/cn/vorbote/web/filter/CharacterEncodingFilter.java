package cn.vorbote.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * CharacterEncodingFilter can set character encoding to request and response.<br>
 * Created at 2022/3/28 15:24
 *
 * @author vorbote
 */
@Slf4j
public class CharacterEncodingFilter implements Filter {

    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Encoding method for request.
     */
    private String requestEncoding;

    /**
     * Encoding method for response.
     */
    private String responseEncoding;

    public CharacterEncodingFilter(String requestEncoding, String responseEncoding) {
        // Make sure the two variables are not null.
        requestEncoding = Optional.ofNullable(requestEncoding).orElse("UTF-8");
        responseEncoding = Optional.ofNullable(responseEncoding).orElse("UTF-8");

        // Set encoding.
        this.requestEncoding = requestEncoding;
        this.responseEncoding = responseEncoding;
    }

    public CharacterEncodingFilter() {
        this("UTF-8", "UTF-8");
    }

    /**
     * Convert encoding for request and response.
     *
     * @param servletRequest  The request.
     * @param servletResponse The response.
     * @param filterChain     The filter chain.
     * @throws IOException      This exception might not be thrown.
     * @throws ServletException This exception might not be thrown.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.setCharacterEncoding(requestEncoding);
        response.setCharacterEncoding(responseEncoding);

        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Character Encoding Filter initializing...");

        this.requestEncoding = Optional.ofNullable(filterConfig.getInitParameter("requestEncoding"))
                .orElse(DEFAULT_ENCODING);
        this.responseEncoding = Optional.ofNullable(filterConfig.getInitParameter("responseEncoding"))
                .orElse(DEFAULT_ENCODING);
    }

    @Override
    public void destroy() {
        log.info("Character Encoding Filter destroyed...");
    }
}
