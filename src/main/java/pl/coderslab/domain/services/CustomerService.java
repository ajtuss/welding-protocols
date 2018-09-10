package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAll();

    void save(CustomerDTO customerDTO);

    CustomerDTO findById(Long id);

    void update(Long id, CustomerDTO customerDTO);

    void remove(Long id);
}
