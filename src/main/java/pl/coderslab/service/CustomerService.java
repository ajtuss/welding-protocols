package pl.coderslab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.coderslab.service.dto.CustomerDTO;
import pl.coderslab.service.dto.MachineDTO;

import java.util.Optional;

public interface CustomerService {

    Page<CustomerDTO> findAll(Pageable pageable);

    CustomerDTO save(CustomerDTO customerDTO);

    Optional<CustomerDTO> findById(Long id);

    void remove(Long id);

    Page<MachineDTO> findAllMachines(Long customerId, Pageable pageable);
}
