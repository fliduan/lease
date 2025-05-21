package fliduan.leasebroker.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarVO {
    private String make;
    private String model;
    private String version;
    private Integer numberOfDoors;
    private BigDecimal grossPrice;
    private BigDecimal nettPrice;
    private Integer emission;
}
