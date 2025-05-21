package fliduan.leasebroker.logging;

import static fliduan.leasebroker.LeaseBrokerConstants.*;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class MDCFilter implements Filter {

    private String appName;

    static final String KEY_API = "api";
    static final String KEY_URI = "uri";

    @Override
    public void destroy() {
        // no implementation needed
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpServletRequest) {
            zetKeyInMDC(KEY_API, appName);
            zetKeyInMDC(CORRELATION_ID , getCorrelationIdFromHeader(httpServletRequest));
            String method = httpServletRequest.getMethod();
            zetKeyInMDC(KEY_URI, (StringUtils.isNotBlank(method) ? method + " " : "") + httpServletRequest.getRequestURI());

            try {
                chain.doFilter(request, response);
            } finally {
                clearMDC();
            }
        }
    }

    protected void zetKeyInMDC(String key, String value) {
        MDC.put(key, value);
    }

    protected void clearMDC() {
        MDC.clear();
    }

    protected String getCorrelationIdFromHeader(final HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID);
        if (StringUtils.isBlank(correlationId)) {
            correlationId = generateUniqueCorrelationId();
        }
        return correlationId;
    }

    private String generateUniqueCorrelationId() {
        return appName + UUID.randomUUID();
    }

    @Override
    public void init(final FilterConfig filterConfig) {
        // no implementation needed
    }

    @Value("${spring.application.name}")
    public void setAppName(String appName) {
        this.appName = appName;
    }
}
