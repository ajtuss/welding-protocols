package pl.coderslab.domain.services;

import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.CustomerDTO;
import pl.coderslab.domain.dto.MachineDTO;
import pl.coderslab.domain.dto.WelderModelDTO;

import java.util.List;


public interface MachineService {

    void save(MachineDTO machineDTO);

    List<MachineDTO> findAll();

    MachineDTO findById(Long id);

    void update(Long id, MachineDTO machineDTO);

    void remove(Long id);

    List<CustomerDTO> findAllCustomers();

    List<BrandDTO> findAllBrands();

    List<BrandDTO> findAllBrands(Long customerId);

    List<WelderModelDTO> findAllMachines(Long customerId, Long brandId);
}
