package cn.vorbote.web.filter;

import cn.vorbote.web.constants.RequestMethod;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * CORS Filter. You can easily handle CORS issues in your web application development by setting this CorsFilter to an
 * appropriate status.
 *
 * @author vorbote
 */
@Slf4j
public class CorsFilter implements Filter {

    private final static String REGEX_URL =
            "(https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()!@:%_+.~#?&/=]*))|(\\*)|(null)";

    private final static List<String> ALL_METHODS = Arrays.asList("GET", "HEAD", "POST", "PUT",
            "DELETE", "CONNECT", "OPTIONS", "TRACE", "PATCH");

    /**
     * According to <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials"
     * >MDN Docs</a>, this tells browsers whether to expose the response to the frontend JavaScript code when
     * the request's credentials mode ({@code Request.credentials}) is included.
     */
    private boolean allowCredentials;

    /**
     * According to <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin"
     * >MDN Docs</a>, this response header indicates whether the response can be shared with requesting code from the
     * given origin.
     */
    private String[] allowOrigin;

    /**
     * According to <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Headers"
     * >MDN Docs</a>, this response header is used in response to a preflight request which includes the
     * {@code Access-Control-Request-Headers} to indicate which HTTP headers can be used during the actual request.
     */
    private String[] allowHeaders;

    /**
     * According to <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Methods"
     * >MDN Docs</a>, this response header specifies one or more methods allowed when accessing a resource in response
     * to a preflight request.
     */
    private RequestMethod[] allowMethods;

    /**
     * According to <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Expose-Headers"
     * >MDN Docs</a>, this response header allows a server to indicate which response headers should be made available
     * to scripts running in the browser, in response to a cross-origin request.<br>
     * Only the <a href="https://developer.mozilla.org/en-US/docs/Glossary/CORS-safelisted_response_header"
     * >CORS-safelisted</a> response headers are exposed by default. For clients to be able to access other headers,
     * the server must list them using the Access-Control-Expose-Headers header.
     */
    private String[] exposeHeaders;

    /**
     * Generate a DIY cors filter.
     *
     * @param allowCredentials According to <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials"
     *                         >MDN Docs</a>, this tells browsers whether to expose the response to the frontend
     *                         JavaScript code when the request's credentials mode ({@code Request.credentials}) is
     *                         included.
     * @param allowOrigin      According to <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin"
     *                         >MDN Docs</a>, this response header indicates whether the response can be shared with
     *                         requesting code from the given origin.
     * @param allowMethods     According to <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Headers"
     *                         >MDN Docs</a>, this response header is used in response to a preflight request which
     *                         includes the {@code Access-Control-Request-Headers} to indicate which HTTP headers can be
     *                         used during the actual request.
     * @param allowHeaders     According to <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Headers"
     *                         >MDN Docs</a>, this response header is used in response to a preflight request which
     *                         includes the {@code Access-Control-Request-Headers} to indicate which HTTP headers can be
     *                         used during the actual request.
     * @param exposeHeaders    According to <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Expose-Headers"
     *                         >MDN Docs</a>, this response header allows a server to indicate which response headers
     *                         should be made available to scripts running in the browser, in response to a cross-origin
     *                         request.<br> Only the <a href="https://developer.mozilla.org/en-US/docs/Glossary/CORS-safelisted_response_header"
     *                         >CORS-safelisted</a> response headers are exposed by default. For clients to be able to
     *                         access other headers, the server must list them using the {@code Access-Control-Expose-Headers}
     *                         header.
     */
    public CorsFilter(boolean allowCredentials,
                      String[] allowOrigin,
                      RequestMethod[] allowMethods,
                      String[] allowHeaders,
                      String[] exposeHeaders) {
        this.allowCredentials = allowCredentials;
        this.allowOrigin = allowOrigin;
        this.allowMethods = allowMethods;
        this.allowHeaders = allowHeaders;
        this.exposeHeaders = exposeHeaders;
    }

    /**
     * Get allow credentials data.
     *
     * @return Value {@code true} if allow credentials, or {@code false}.
     */
    protected boolean isAllowCredentials() {
        return allowCredentials;
    }

    protected String[] getAllowOrigin() {
        return allowOrigin;
    }

    protected String[] getAllowHeaders() {
        return allowHeaders;
    }

    protected RequestMethod[] getAllowMethods() {
        return allowMethods;
    }

    protected String[] getExposeHeaders() {
        return exposeHeaders;
    }

    /**
     * Transfer {@code String} array to MDN specified format of header value.
     *
     * @param array The {@code String} array.
     * @return A {@code String} with the format of MDN specified header value.
     */
    private static String fromArray(String[] array) {
        StringBuilder builder = new StringBuilder();
        if (array != null && array.length != 0) {
            for (String item : array) {
                builder.append(item).append(",");
            }
        }
        return builder.length() != 0 ? builder.substring(0, builder.length() - 1) : "";
    }

    /**
     * Generate a default cors filter (cannot solve the {@code cors} problem).
     */
    public CorsFilter() {
        this(false, null, null, null, null);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        // Transform original ServletXxx instances to HttpServletXxx instances.
        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;

        var allowedOrigin = Optional.ofNullable(allowOrigin)
                .map((item) -> item.length)
                .map((item) -> {
                    if (item > 1) {
                        var origin = Optional.ofNullable(request.getHeader("Origin")).orElse("");
                        if (Arrays.stream(allowOrigin).anyMatch((anyOrigin) -> anyOrigin.equalsIgnoreCase(origin))) {
                            return origin;
                        }
                    }
                    return allowOrigin[0];
                })
                .orElse("");

        var allowedMethods = Arrays.toString(allowMethods);

        // Handle CORS problem.
        response.addHeader("Access-Control-Allow-Credentials", String.valueOf(isAllowCredentials()));
        response.addHeader("Access-Control-Allow-Origin", allowedOrigin);
        response.addHeader("Access-Control-Allow-Methods", allowedMethods.substring(1, allowedMethods.length() - 1));
        response.addHeader("Access-Control-Allow-Headers", fromArray(getAllowHeaders()));

        // Set exposed response headers
        response.addHeader("Access-Control-Expose-Headers", fromArray(getExposeHeaders()));

        if ("*".equalsIgnoreCase(allowedOrigin) || "null".equalsIgnoreCase(allowedOrigin)) {
            response.addHeader("Vary", "Origin");
        }

        // all xhr requests will send a options request at first, therefore intercept all options requests.
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            // log.info("User-Agent: {}", req.getHeader("User-Agent"));
            return;
        }

        // CORS Response Header has been added properly.
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("CorsFilter initializing...");

        if (filterConfig.getInitParameterNames().hasMoreElements()) {
            log.debug("Detected config is from web.xml file, using filterConfig to deploy.");
            // set the property - allow credentials
            log.debug("Initializing property [allowCredentials]");
            this.allowCredentials = Optional.ofNullable(filterConfig.getInitParameter("allowCredentials"))
                    .map(Boolean::valueOf)
                    .orElse(false);
            log.debug("Property [allowCredentials] initialized, value has been set to [{}]", allowCredentials);

            // set the property - allow origin
            log.debug("Initializing property [allowOrigin]");
            String[] tmpAllowOrigin = Optional.ofNullable(filterConfig.getInitParameter("allowOrigin"))
                    .map((value) -> value.split(",( )?"))
                    .orElse(new String[]{});

            if (Arrays.stream(tmpAllowOrigin).allMatch((item) -> {
                boolean checkResult = item.matches(REGEX_URL);
                if (!checkResult) {
                    log.error("Origin [{}] does not like a web url, consider remove it?", item);
                }
                return checkResult;
            })) {
                allowOrigin = tmpAllowOrigin;
            }
            log.debug("Property [allowOrigin] initialized, value has been set to {}", Arrays.toString(allowOrigin));

            // set the property - allow headers
            log.debug("Initializing property [allowHeaders]");
            allowHeaders = Optional.ofNullable(filterConfig.getInitParameter("allowHeaders"))
                    .map((value) -> value.split(",( )?"))
                    .orElse(new String[]{});
            log.debug("Property [allowHeaders] initialized, value has been set to {}", Arrays.toString(allowHeaders));

            // set the property - allow methods
            log.debug("Initializing property [allowMethods]");
            var tmpAllowMethods = Optional.ofNullable(filterConfig.getInitParameter("allowMethods"))
                    .map((value) -> value.split(",( )?"))
                    .orElse(new String[]{});

            if (Arrays.stream(tmpAllowMethods).allMatch((item) -> {
                boolean checkResult = ALL_METHODS.contains(item.toUpperCase());
                if (!checkResult) {
                    log.error("RequestMethod [{}] does not like a web request method, consider remove it?", item);
                }
                return checkResult;
            })) {
                try {
                    var allowMethodList = new ArrayList<RequestMethod>();
                    for (var method : tmpAllowMethods) {
                        allowMethodList.add(RequestMethod.valueOf(method));
                    }
                    allowMethods = allowMethodList.toArray(new RequestMethod[]{});
                } catch (IllegalArgumentException exception) {
                    log.error("You might delivered a wrong request method, only these methods will be accepted: {}", ALL_METHODS);
                    allowMethods = new RequestMethod[]{RequestMethod.GET, RequestMethod.POST};
                }
            }
            log.debug("Property [allowMethods] initialized, value has been set to {}", Arrays.toString(allowMethods));

            // set the property - expose headers
            log.debug("Initializing property [exposeHeaders]");
            this.exposeHeaders = Optional.ofNullable(filterConfig.getInitParameter("exposeHeaders"))
                    .map((value) -> value.split(",( )?"))
                    .orElse(new String[]{});
            log.debug("Property [exposeHeaders] initialized, value has been set to {}", Arrays.toString(exposeHeaders));
        } else {
            log.debug("Detected config is from Spring, using application.(yml)/(properties) to deploy.");
        }
    }

    @Override
    public void destroy() {
        log.info("CorsFilter destroyed...");
    }
}
