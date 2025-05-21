package fliduan.leasebroker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LeaseBrokerException extends RuntimeException{

    private final HttpStatus httpStatus;

    public LeaseBrokerException(final String message) {
        super(message);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public LeaseBrokerException(final String message, final HttpStatus returnStatus) {
        super(message);
        httpStatus = returnStatus;
    }

    public LeaseBrokerException(final String message, final Throwable throwable) {
        super(message, throwable);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public LeaseBrokerException(final String message, final Throwable throwable, final HttpStatus returnStatus) {
        super(message, throwable);
        httpStatus = returnStatus;
    }
}
