package fliduan.leasecompany.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String make;
    private String model;
    private String version;
    private Integer numberOfDoors;
    private BigDecimal grossPrice;
    private BigDecimal nettPrice;
    private Integer emission;
}
