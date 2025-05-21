package fliduan.leasebroker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "QuoteRequest",
        description = "Request to get quote for a car lease.")
public class QuoteRequestDto {
    @NotBlank(message="Car maker is mandatory." )
    private String make;
    @NotBlank(message="Car model is mandatory." )
    private String model;
    @NotBlank(message="Car version is mandatory." )
    private String version;

    @NotNull(message="The mileage is mandatory." )
    @Schema(description="The amount of kilometers on annual base.", example="10000")
    private Integer mileage;

    @NotNull(message="Duration of the contract is mandatory." )
    @Schema(description="Duration of the contract in months.", example="48")
    private Integer duration;

    @NotNull(message="The start date  is mandatory." )
    private LocalDate startDate;

}
