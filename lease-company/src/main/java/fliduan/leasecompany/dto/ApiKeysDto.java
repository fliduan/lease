package fliduan.leasecompany.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString(doNotUseGetters = true)
@Builder
@Schema(description = "List of api keys for the clients.")
@SuppressWarnings("java:S1068")
public class ApiKeysDto {
    List<ApiKeyDto> apiKeys;
}
