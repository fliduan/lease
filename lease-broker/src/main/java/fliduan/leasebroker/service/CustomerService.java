package fliduan.leasebroker.service;

import fliduan.leasebroker.domain.Customer;
import fliduan.leasebroker.dto.CustomerDto;
import fliduan.leasebroker.dto.CustomerRequestDto;
import fliduan.leasebroker.mapper.CustomerMapper;
import fliduan.leasebroker.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    /**
     * Method to retrieve customer information
     * @param customerNumber customer number
     * @return @{@link CustomerDto} customer information
     */
    public CustomerDto retrieveCustomer(String customerNumber){
        Customer customer = customerRepository.findCustomerByCustomerNumber(customerNumber);
        if (customer != null){
            return customerMapper.customerToCustomerDto(customer);
        } else {
            throw new EntityNotFoundException("Customer is not found.");
        }
    }

    /**
     * Method to retrieve customers information
     * @param customerRequestDto request to get customer information
     * @return {@link List <CarDto>} list of cars
     */
    public List<CustomerDto> retrieveCustomers(@NotNull CustomerRequestDto customerRequestDto){
        List<Customer> customers = customerRepository.retrieveCustomers(customerRequestDto.getEmail()
                , customerRequestDto.getLastName()
                , customerRequestDto.getHouseNumber()
                , customerRequestDto.getZipCode()
                , customerRequestDto.getPhoneNumber());

        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer: customers){
            customerDtoList.add(customerMapper.customerToCustomerDto(customer));
        }
        return customerDtoList;
    }

    /**
     * Method to save customer information
     * @param customerDto customer information
     */
    public void saveCustomer(@NotNull CustomerDto customerDto){
        Customer customer = customerMapper.customerDtoToCustomer(customerDto);
        customerRepository.save(customer);
    }

    /**
     * Method to update customer information
     * @param customerDto customer information
     */
    public void updateCustomer(@NotNull CustomerDto customerDto){
        var customer = customerRepository.findCustomerByCustomerNumber(customerDto.getCustomerNumber());
        if (customer != null){
            customer.setEmail(customerDto.getEmail());
            customer.setFirstName(customerDto.getFirstName());
            customer.setLastName(customerDto.getLastName());
            customer.setStreet(customerDto.getStreet());
            customer.setHouseNumber(customerDto.getHouseNumber());
            customer.setZipCode(customerDto.getZipCode());
            customer.setPlace(customerDto.getPlace());
            customer.setPhoneNumber(customerDto.getPhoneNumber());
            customerRepository.save(customer);
        }
        else {
            throw new EntityNotFoundException("Customer information is not found.");
        }
    }

    /**
     * Method to delete customer information
     * @param customerNumber customer number
     */
    public void deleteCustomer(String customerNumber){
        Customer customer = customerRepository.findCustomerByCustomerNumber(customerNumber);
        if (customer != null){
            customerRepository.deleteById(customer.getId());
        } else {
            throw new EntityNotFoundException("Customer is not found.");
        }
    }
}
