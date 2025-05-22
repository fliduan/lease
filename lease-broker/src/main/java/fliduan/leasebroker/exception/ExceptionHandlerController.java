package fliduan.leasebroker.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;

@Log4j2
@RestControllerAdvice
public class ExceptionHandlerController {
    protected static final String ALGEMENE_EXCEPTIE_MSG = "Global exception has occurred";
    protected static final String EXCEPTIE_OPGETREDEN_MSG = "An exception has occurred";
    protected static final String DATA_NOT_FOUND_MSG = "Data not found exception has occurred";
    protected static final String RESOURCE_NOT_FOUND_MSG = "Resource not found exception has occurred";
    protected static final String DATA_INTEGRITY_MSG = "Data integrity exception has occurred";
    protected static final String METHOD_NOT_ALLOWED_MSG = "Method not allowed exception has occurred";
    protected static final String UNSUPPORTED_MEDIA_TYPE_MSG = "Unsupported media type exception has occurred";
    protected static final String VALIDATIE_ERROR_MSG = "A validation exception has occurred";

    /**
     * Handler for MethodArgumentTypeMismatchException if the request object is of wrong type.
     *
     * @param ex Exception
     * @return error message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<ApiErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.warn(VALIDATIE_ERROR_MSG + ": {}", new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.toString()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        VALIDATIE_ERROR_MSG));
    }

    /**
     * Handler for HttpMessageNotReadableException.
     *
     * @param ex Exception
     * @return error message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ApiErrorResponse> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn(VALIDATIE_ERROR_MSG + ": {}", new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.toString()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        VALIDATIE_ERROR_MSG));
    }

    /**
     * Handler for MethodArgumentNotValidException if the request object is not valid.
     *
     * @param ex Exception
     * @return All validation error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, List<String>>();
        ex.getBindingResult().getAllErrors().forEach(error -> errors.put(
                error instanceof FieldError fieldError ? fieldError.getField() :
                        error.getObjectName(), error.getDefaultMessage() != null ? List.of(error.getDefaultMessage())
                        : null)
        );

        log.warn(VALIDATIE_ERROR_MSG + ": {}", new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.toString(), errors));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        VALIDATIE_ERROR_MSG));
    }

    /**
     * Handler for ConstraintViolationException if any pathVariable and requestParam is not valid.
     * @param ex ConstraintViolationException
     * @return validation error message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn(VALIDATIE_ERROR_MSG + ": {}", new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.toString()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        VALIDATIE_ERROR_MSG));
    }

    /**
     * Handler for EntityNotFoundException.
     *
     * @param ex EntityNotFoundException
     * @return error message
     */
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ApiErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        log.info(DATA_NOT_FOUND_MSG + ": {}", new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase(), ex.toString()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                        DATA_NOT_FOUND_MSG));
    }

    /**
     * Handler for HttpRequestMethodNotSupportedException.
     *
     * @param ex HttpRequestMethodNotSupportedException
     * @return error message
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ApiErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        log.info(METHOD_NOT_ALLOWED_MSG + ": {}", new ApiErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(),HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), ex.toString()));

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new ApiErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(),
                        HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), METHOD_NOT_ALLOWED_MSG));
    }

    /**
     * Handler for HttpMediaTypeException.
     *
     * @param ex HttpMediaTypeException
     * @return error message
     */
    @ExceptionHandler(HttpMediaTypeException.class)
    ResponseEntity<ApiErrorResponse> handleHttpMediaTypeException(HttpMediaTypeException ex) {
        log.info(UNSUPPORTED_MEDIA_TYPE_MSG + ": {}", new ApiErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), ex.toString()));

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(
                new ApiErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), UNSUPPORTED_MEDIA_TYPE_MSG));
    }


    /**
     * Handler for ValidationException.
     *
     * @param ex ValidationException
     * @return error message
     */
    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ApiErrorResponse> handleValidationException(ValidationException ex) {
        log.warn(VALIDATIE_ERROR_MSG + ": {}", new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.toString()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(), VALIDATIE_ERROR_MSG));
    }

    /**
     * Handler for DataIntegrityViolationException.
     *
     * @param ex DataIntegrityViolationException
     * @return error message
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ApiErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.warn(DATA_INTEGRITY_MSG + ": {}", new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), ex.toString()));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), DATA_INTEGRITY_MSG));
    }

    /**
     * Handler for handle MissingServletRequestParameter Or Part Exception
     *
     * @param ex Exception
     * @return error message
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, MissingServletRequestPartException.class})
    ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameterOrPart(Exception ex) {
        log.error(VALIDATIE_ERROR_MSG + ": {}", new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.toString()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(), VALIDATIE_ERROR_MSG));
    }

    /**
     * Handler for AccessDeniedException.
     *
     * @param ex AccessDeniedException
     * @return error message
     */
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(EXCEPTIE_OPGETREDEN_MSG + ": {}", new ApiErrorResponse(HttpStatus.FORBIDDEN.value(),HttpStatus.FORBIDDEN.getReasonPhrase(), ex.toString()));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ApiErrorResponse(HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.getReasonPhrase()));
    }
    /**
     * Handler for LeaseBrokerException
     *
     * @param ex LeaseBrokerException
     * @return error message
     */
    @ExceptionHandler(LeaseBrokerException.class)
    ResponseEntity<ApiErrorResponse> handleLeaseBrokerException(LeaseBrokerException ex) {
        log.error(EXCEPTIE_OPGETREDEN_MSG + ": {}", new ApiErrorResponse(ex.getHttpStatus().value(),ex.getHttpStatus().getReasonPhrase(), ex.toString()));

        return ResponseEntity.status(ex.getHttpStatus()).body(
                new ApiErrorResponse(ex.getHttpStatus().value(), ex.getHttpStatus().getReasonPhrase(), ex.getLocalizedMessage()));
    }

    /**
     * Handler for NoResourceFoundException
     *
     * @param ex NoResourceFoundException
     * @return error message
     */
    @ExceptionHandler(NoResourceFoundException.class)
    ResponseEntity<ApiErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.error(RESOURCE_NOT_FOUND_MSG + ": {}", new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase(), ex.toString()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getLocalizedMessage()));
    }

    /**
     * Global handler for all uncatched exceptions
     *
     * @param ex Exception
     * @return error message
     */
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiErrorResponse> handleGlobalException(Exception ex) {
        log.error(ALGEMENE_EXCEPTIE_MSG + ": {}", new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.toString()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ALGEMENE_EXCEPTIE_MSG));
    }
}
