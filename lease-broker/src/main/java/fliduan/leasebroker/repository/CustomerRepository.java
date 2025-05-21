package fliduan.leasebroker.repository;

import fliduan.leasebroker.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerByCustomerNumber(String customerNumber);

    @Query("select c from Customer c where (:email is null or c.email=:email)" +
            " and (:lastName is null or c.lastName=:lastName)" +
            " and (:houseNumber is null or c.houseNumber=:houseNumber)" +
            " and (:zipCode is null or c.zipCode=:zipCode)" +
            " and (:phoneNumber is null or c.phoneNumber=:phoneNumber)")
    List<Customer> retrieveCustomers(@Param("email") String email, @Param("lastName") String lastName
            , @Param("houseNumber") String houseNumber, @Param("zipCode") String zipCode
            , @Param("phoneNumber") String phoneNumber);
}