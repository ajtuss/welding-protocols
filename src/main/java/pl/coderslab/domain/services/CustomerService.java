package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.dto.MachineDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAll();

    CustomerDTO save(CustomerDTO customerDTO);

    CustomerDTO findById(Long id);

    CustomerDTO update(CustomerDTO customerDTO);

    void remove(Long id);

    List<MachineDTO> findAllMachines(Long customerId);
}
