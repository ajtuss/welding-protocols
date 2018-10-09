package pl.coderslab.service;

import pl.coderslab.service.dto.CustomerDTO;
import pl.coderslab.service.dto.MachineDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAll();

    CustomerDTO save(CustomerDTO customerDTO);

    CustomerDTO findById(Long id);

    CustomerDTO update(CustomerDTO customerDTO);

    void remove(Long id);

    List<MachineDTO> findAllMachines(Long customerId);
}
