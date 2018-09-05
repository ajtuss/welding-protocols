package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> findAll();

    void save(CustomerDto customerDto);

    CustomerDto findById(Long id);

    void update(Long id, CustomerDto customerDto);

    void remove(Long id);
}
