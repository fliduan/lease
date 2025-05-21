package fliduan.leasebroker.controller;

import fliduan.leasebroker.dto.QuoteRequestDto;
import fliduan.leasebroker.dto.QuoteResponseDto;
import fliduan.leasebroker.exception.ApiErrorResponse;
import fliduan.leasebroker.logging.LogUtil;
import fliduan.leasebroker.service.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/quotes")
public class QuoteController {
    private final QuoteService quoteService;
    private final LogUtil logUtil;

    @PostMapping
    @Operation(summary="Request for a lease car quote.",
            description="Request for a lease car quote.",
            parameters = {@Parameter(in = ParameterIn.HEADER, description = "Correlation ID", name = "CID", schema = @Schema(type="string"))}
    )
    @ApiResponse(responseCode = "200", description = "the request is processed successfully")
    @ApiResponse(responseCode = "400", description = "bad request",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "401", description = "missing token",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "403", description = "not authorized",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "500", description = "unexpected exception",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    public ResponseEntity<QuoteResponseDto> requestQuote(@RequestBody @Validated final QuoteRequestDto quoteRequestDto) {
        var response = quoteService.requestQuote(quoteRequestDto);
        logUtil.logApiResponse("requestQuote", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }
}
