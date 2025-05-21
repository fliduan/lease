package fliduan.leasecompany.controller;

import fliduan.leasecompany.dto.CarDto;
import fliduan.leasecompany.dto.CarRequestDto;
import fliduan.leasecompany.exception.ApiErrorResponse;
import fliduan.leasecompany.logging.LogUtil;
import fliduan.leasecompany.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/cars")
@SecurityRequirement(name="X-Api-Key")
@Validated
public class CarController {
    private final CarService carService;
    private final LogUtil logUtil;

    @PostMapping
    @Operation(summary="Save car information.",
            description="Save car information.",
            parameters = {@Parameter(in = ParameterIn.HEADER, description = "Correlation ID", name = "CID", schema = @Schema(type="string"))}
    )
    @ApiResponse(responseCode = "200", description = "the request is processed successfully")
    @ApiResponse(responseCode = "400", description = "bad request",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "401", description = "missing token",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "403", description = "not authorized",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "500", description = "unexpected exception",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    public ResponseEntity<Void> saveCar(@RequestBody @Validated final CarDto carDto) {
        carService.saveCar(carDto);
        logUtil.logApiResponse("saveCar", HttpStatus.OK);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary="Update car information.",
            description="Update car information.",
            parameters = {@Parameter(in = ParameterIn.HEADER, description = "Correlation ID", name = "CID", schema = @Schema(type="string"))}
    )
    @ApiResponse(responseCode = "200", description = "the request is processed successfully")
    @ApiResponse(responseCode = "400", description = "bad request",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "401", description = "missing token",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "403", description = "not authorized",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "500", description = "unexpected exception",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    public ResponseEntity<Void> updateCar(@RequestBody @Validated final CarDto carDto) {
        carService.updateCar(carDto);
        logUtil.logApiResponse("updateCar", HttpStatus.OK);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = "application/json")
    @Operation(summary="Delete car information based on make, model, version.",
            description="Delete cars information based on make, model, version.",
            parameters = {@Parameter(in = ParameterIn.HEADER, description = "Correlation ID", name = "CID", schema = @Schema(type="string"))}
    )
    @ApiResponse(responseCode = "200", description = "the request is processed successfully")
    @ApiResponse(responseCode = "400", description = "bad request",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "401", description = "missing token",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "403", description = "not authorized",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "500", description = "unexpected exception",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    public ResponseEntity<Void> deleteCar(@RequestBody @Validated final CarRequestDto carRequestDto){
        carService.deleteCar(carRequestDto);

        logUtil.logApiResponse("deleteCar", HttpStatus.OK);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = "application/json")
    @Operation(summary="Get cars information based on make, (optional) model, (optional) version.",
            description="Get cars information based on make, (optional) model, (optional) version.",
            parameters = {@Parameter(in = ParameterIn.HEADER, description = "Correlation ID", name = "CID", schema = @Schema(type="string"))}
    )
    @ApiResponse(responseCode = "200", description = "the request is processed successfully")
    @ApiResponse(responseCode = "400", description = "bad request",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "401", description = "missing token",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "403", description = "not authorized",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "500", description = "unexpected exception",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))})
    public ResponseEntity<List<CarDto>> getCars(@ParameterObject @Validated final CarRequestDto carRequestDto){
        List<CarDto> cars = carService.retrieveCars(carRequestDto);

        logUtil.logApiResponse("getCars", HttpStatus.OK);
        return ResponseEntity.ok(cars);
    }

}
