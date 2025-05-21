package fliduan.leasecompany.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString(doNotUseGetters = true)
@Builder
@Schema(description = "Api Key for a client")
public class ApiKeyDto {
    private String client;
    private String key1;
    private String key2;
}
