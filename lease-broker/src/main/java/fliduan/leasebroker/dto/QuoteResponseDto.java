package fliduan.leasebroker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "QuoteResponse",
        description = "Response for quote for a car lease.")
public class QuoteResponseDto {
    private String make;
    private String model;
    private String version;
    private Integer mileage;
    private Integer duration;
    private BigDecimal percentInterestRate;
    private BigDecimal nettPrice;
    private BigDecimal leaseRate;
}
