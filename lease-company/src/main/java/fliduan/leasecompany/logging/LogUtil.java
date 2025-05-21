package fliduan.leasecompany.logging;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class LogUtil {
    private static final String API_CALL_LOG_FORMAT = "Call to %s - %s, request parameters: %s";
    private static final String API_RESPONSE_LOG_FORMAT = "Response from %s - %s: HttpStatus %s";
    private static final String API_KEY_CLIENT_LOG_FORMAT = "Call to %s with key from %s";
    private static final String API_CALL_RETRY_LOG_FORMAT = "Retry call to %s - %s";
    private static final String API_CALL_RETRY_DETAIL_LOG_FORMAT = "Retry call to %s - %s after exception: %s";

    private String appName;
    public void logApiCall(String api, String endpoint, String parameters) {
        log.info(String.format(API_CALL_LOG_FORMAT, api, endpoint, parameters));
    }

    public void logApiResponse(String methodName, HttpStatus status) {
        log.info(String.format(API_RESPONSE_LOG_FORMAT, appName, methodName, status));
    }

    public void logApiKeyClient(String client) {
        log.info(String.format(API_KEY_CLIENT_LOG_FORMAT, appName, client));
    }

    public void logApiCallRetry(String api, String endpoint) {
        log.info(String.format(API_CALL_RETRY_LOG_FORMAT, api, endpoint));
    }

    public void logApiCallRetry(String api, String endpoint, String errorMessage ) {
        log.info(String.format(API_CALL_RETRY_DETAIL_LOG_FORMAT, api, endpoint, errorMessage));
    }

    @Value("${spring.application.name}")
    public void setAppName(String appName) {
        this.appName = appName;
    }
}
