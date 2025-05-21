package fliduan.leasebroker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CustomerRequest",
        description = "Request to get customers information for the lease.")
public class CustomerRequestDto {
    private String email;
    private String lastName;
    private String houseNumber;
    private String zipCode;
    private String phoneNumber;
}
