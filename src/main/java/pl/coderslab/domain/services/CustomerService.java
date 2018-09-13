package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.CustomerCreationDTO;
import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.dto.CustomerUpdateDTO;
import pl.coderslab.domain.dto.MachineDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAll();

    CustomerDTO save(CustomerCreationDTO customerDTO);

    CustomerDTO findById(Long id);

    CustomerDTO update(CustomerUpdateDTO customerDTO);

    void remove(Long id);

    List<MachineDTO> findAllMachines(Long customerId);
}
