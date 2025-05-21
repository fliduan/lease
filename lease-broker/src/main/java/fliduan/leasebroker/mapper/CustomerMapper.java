package fliduan.leasebroker.mapper;

import fliduan.leasebroker.domain.Customer;
import fliduan.leasebroker.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto customerToCustomerDto(Customer customer);
    Customer customerDtoToCustomer(CustomerDto customerDto);
}
