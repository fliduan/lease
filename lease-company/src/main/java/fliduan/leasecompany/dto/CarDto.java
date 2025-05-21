package fliduan.leasecompany.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CarDto",
        description = "Car information for the lease.")
public class CarDto {
    @NotBlank(message="Car maker is mandatory." )
    private String make;
    @NotBlank(message="Car model is mandatory." )
    private String model;
    @NotBlank(message="Car version is mandatory." )
    private String version;

    @Schema(description="Number of doors of the car.", example="4")
    private Integer numberOfDoors;

    private BigDecimal grossPrice;

    @NotNull(message="Car nett price is mandatory." )
    private BigDecimal nettPrice;

    @Schema(description="C02 emission of the car.", example="143")
    private Integer emission;
}
