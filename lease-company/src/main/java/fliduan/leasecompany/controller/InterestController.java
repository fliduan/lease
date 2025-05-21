package fliduan.leasecompany.controller;

import fliduan.leasecompany.dto.InterestResponseDto;
import fliduan.leasecompany.exception.ApiErrorResponse;
import fliduan.leasecompany.logging.LogUtil;
import fliduan.leasecompany.service.InterestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/interests")
@SecurityRequirement(name="X-Api-Key")
@Validated
public class InterestController {
    private final InterestService interestService;
    private final LogUtil logUtil;

    @GetMapping(produces = "application/json")
    @Operation(summary="Get interest rate based on start date.",
            description="Get interest rate based on start date.",
            parameters = {@Parameter(in = ParameterIn.HEADER, description = "Correlation ID", name = "CID", schema = @Schema(type="string"))}
    )
    @ApiResponse(responseCode = "200", description = "the request is processed successfully")
    @ApiResponse(responseCode = "400", description = "bad request",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "401", description = "missing token",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "403", description = "not authorized",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "500", description = "unexpected exception",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    public ResponseEntity<InterestResponseDto> getInterestRate(@RequestParam LocalDate startDate){
        var response = interestService.getInterestRate(startDate);
        logUtil.logApiResponse("getInterestRate", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

}
