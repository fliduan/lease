package fliduan.leasecompany.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Map;

/**
 * Api Error response POJO.
 */
@Getter
@EqualsAndHashCode
@Schema(description = "Generic Error Message")
@Log4j2
public class ApiErrorResponse {
    @Schema(description = "HTTP error code", accessMode = Schema.AccessMode.READ_ONLY)
    private final int status;
    @Schema(description = "HTTP error name", accessMode = Schema.AccessMode.READ_ONLY)
    private final String title;
    @Schema(description = "Error description", accessMode = Schema.AccessMode.READ_ONLY)
    private final String detail;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Error list", accessMode = Schema.AccessMode.READ_ONLY)
    private Map<String, List<String>> errors;


    /**
     * ApiErrorResponse constructor with validation errors
     *
     * @param status status
     * @param title  title
     * @param detail detail
     * @param errors validation errors
     */
    public ApiErrorResponse(int status, String title, String detail,
                            Map<String, List<String>> errors) {
        this.status = status;
        this.title = title;
        this.detail = detail;
        this.errors = errors;
    }

    /**
     * ApiErrorResponse constructor.
     *
     * @param status status
     * @param title  title
     * @param detail detail
     */
    public ApiErrorResponse(int status, String title, String detail) {
        this.status = status;
        this.title = title;
        this.detail = detail;
    }

    /**
     * ApiErrorResponse constructor.
     * detail will be the same value as title.
     *
     * @param status status
     * @param title  title
     */
    @ConstructorProperties({"status", "title"})
    public ApiErrorResponse(int status, String title) {
        this.status = status;
        this.title = title;
        this.detail = title;
    }

    /**
     * ApiErrorResponse constructor for HttpStatus.
     *
     * @param httpStatus HttpStatus.
     */
    public ApiErrorResponse(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.title = httpStatus.getReasonPhrase();
        this.detail = httpStatus.getReasonPhrase();
    }

    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.warn("Error occurred in print jackson: {}", e.toString());
            return "";
        }
    }
}
