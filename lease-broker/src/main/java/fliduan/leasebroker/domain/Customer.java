package fliduan.leasebroker.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_number", unique=true)
    private String customerNumber;

    private String email;
    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    private String street;
    @Column(name="house_number")
    private String houseNumber;

    @Column(name="zip_code")
    private String zipCode;
    private String place;

    @Column(name="phone_number")
    private String phoneNumber;
}
