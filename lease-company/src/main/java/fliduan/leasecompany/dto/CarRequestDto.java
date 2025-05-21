package fliduan.leasecompany.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@EqualsAndHashCode
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CarRequestDto",
        description = "Request to get cars information.")
public class CarRequestDto {
    @NotBlank(message="Car maker is mandatory." )
    private String make;
    private String model;
    private String version;
}
