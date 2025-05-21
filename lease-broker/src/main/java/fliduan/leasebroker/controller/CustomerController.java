package fliduan.leasebroker.controller;

import fliduan.leasebroker.dto.CustomerDto;
import fliduan.leasebroker.dto.CustomerRequestDto;
import fliduan.leasebroker.exception.ApiErrorResponse;
import fliduan.leasebroker.logging.LogUtil;
import fliduan.leasebroker.service.CustomerService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final LogUtil logUtil;

    @PostMapping
    @Operation(summary="Save customer information.",
            description="Save customer information.",
            parameters = {@Parameter(in = ParameterIn.HEADER, description = "Correlation ID", name = "CID", schema = @Schema(type="string"))}
    )
    @ApiResponse(responseCode = "200", description = "the request is processed successfully")
    @ApiResponse(responseCode = "400", description = "bad request",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "401", description = "missing token",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "403", description = "not authorized",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "500", description = "unexpected exception",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    public ResponseEntity<Void> saveCustomer(@RequestBody @Validated final CustomerDto customerDto) {
        customerService.saveCustomer(customerDto);
        logUtil.logApiResponse("saveCustomer", HttpStatus.OK);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary="Update customer information.",
            description="Update customer information.",
            parameters = {@Parameter(in = ParameterIn.HEADER, description = "Correlation ID", name = "CID", schema = @Schema(type="string"))}
    )
    @ApiResponse(responseCode = "200", description = "the request is processed successfully")
    @ApiResponse(responseCode = "400", description = "bad request",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "401", description = "missing token",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "403", description = "not authorized",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "500", description = "unexpected exception",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    public ResponseEntity<Void> updateCustomer(@RequestBody @Validated final CustomerDto customerDto) {
        customerService.updateCustomer(customerDto);
        logUtil.logApiResponse("updateCustomer", HttpStatus.OK);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{customerNumber}",produces = "application/json")
    @Operation(summary="Get customer information based on customer number.",
            description="Get customer information based on customer number.",
            parameters = {@Parameter(in = ParameterIn.HEADER, description = "Correlation ID", name = "CID", schema = @Schema(type="string"))}
    )
    @ApiResponse(responseCode = "200", description = "the request is processed successfully")
    @ApiResponse(responseCode = "400", description = "bad request",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "401", description = "missing token",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "403", description = "not authorized",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "500", description = "unexpected exception",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerNumber){
        customerService.deleteCustomer(customerNumber);

        logUtil.logApiResponse("deleteCustomer", HttpStatus.OK);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{customerNumber}",produces = "application/json")
    @Operation(summary="Get customer information based on customer number.",
            description="Get customer information based on customer number.",
            parameters = {@Parameter(in = ParameterIn.HEADER, description = "Correlation ID", name = "CID", schema = @Schema(type="string"))}
    )
    @ApiResponse(responseCode = "200", description = "the request is processed successfully")
    @ApiResponse(responseCode = "400", description = "bad request",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "401", description = "missing token",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "403", description = "not authorized",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "500", description = "unexpected exception",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable String customerNumber){
        CustomerDto customerDto = customerService.retrieveCustomer(customerNumber);

        logUtil.logApiResponse("getCustomer", HttpStatus.OK);
        return ResponseEntity.ok(customerDto);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary="Get customers information based on the given criteria.",
            description="Get customers information Customize Toolbar…",
            parameters = {@Parameter(in = ParameterIn.HEADER, description = "Correlation ID", name = "CID", schema = @Schema(type="string"))}
    )
    @ApiResponse(responseCode = "200", description = "the request is processed successfully")
    @ApiResponse(responseCode = "400", description = "bad request",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "401", description = "missing token",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "403", description = "not authorized",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "500", description = "unexpected exception",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    public ResponseEntity<List<CustomerDto>> getCustomers(@RequestBody @Validated final CustomerRequestDto customerRequestDto){
        List<CustomerDto> customers = customerService.retrieveCustomers(customerRequestDto);

        logUtil.logApiResponse("getCustomers", HttpStatus.OK);
        return ResponseEntity.ok(customers);
    }

}
