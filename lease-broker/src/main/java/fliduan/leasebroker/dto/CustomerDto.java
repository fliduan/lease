package fliduan.leasebroker.dto;

import fliduan.leasebroker.LeaseBrokerConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@EqualsAndHashCode
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Customer",
        description = "Customer information for the lease.")
public class CustomerDto {
    @NotNull(message="Customer number is mandatory." )
    private String customerNumber;

    @NotBlank(message="Customer email adress is mandatory." )
    @Pattern(regexp = LeaseBrokerConstants.OPTIONAL_EMAIL_REGEX, message = "Email adres is niet correct")
    @Schema(description="email address of the customer.", example="test01@test.org")
    private String email;

    private String firstName;

    @NotBlank(message="Customer last name is mandatory." )
    private String lastName;

    private String street;
    private String houseNumber;

    @Schema(description = "Zip code of the customer address", example="1234AB")
    private String zipCode;
    private String place;

    @NotBlank(message="Customer phone number is mandatory." )
    @Pattern(regexp = LeaseBrokerConstants.TELEFOON_REGEX, message = "Phone number is not valid.")
    @Schema(description="Phone number of the customer.", example="06-12345678")
    private String phoneNumber;
}
