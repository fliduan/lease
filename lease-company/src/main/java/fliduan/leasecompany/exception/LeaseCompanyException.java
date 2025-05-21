package fliduan.leasecompany.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LeaseCompanyException extends RuntimeException{

    private final HttpStatus httpStatus;

    public LeaseCompanyException(final String message) {
        super(message);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public LeaseCompanyException(final String message, final HttpStatus returnStatus) {
        super(message);
        httpStatus = returnStatus;
    }

    public LeaseCompanyException(final String message, final Throwable throwable) {
        super(message, throwable);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public LeaseCompanyException(final String message, final Throwable throwable, final HttpStatus returnStatus) {
        super(message, throwable);
        httpStatus = returnStatus;
    }
}
