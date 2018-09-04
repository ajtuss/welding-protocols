package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> findAll();

    void saveCustomer(CustomerDto customerDto);

    CustomerDto findById(Long id);

    void updateCustomer(Long id, CustomerDto customerDto);
}
